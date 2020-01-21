package oss.transaction.Bll.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import oss.transaction.Bll.Dtos.TransactionForDetailedDto;
import oss.transaction.Bll.Dtos.TransactionForListDto;
import oss.transaction.Bll.Dtos.TransactionToCreateDto;
import oss.transaction.Bll.Enums.Status;
import oss.transaction.Bll.Enums.TransactionType;
import oss.transaction.Dal.Models.Transaction;
import oss.transaction.Dal.Repositories.*;
import oss.transaction.Bll.Interfaces.ITransactionService;

import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;
import java.util.Random;

@Service
public class TransactionService implements ITransactionService {

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private TransactionTypeRepository transactionTypeRepository;

    @Autowired
    private PaymentInstrumentRepository paymentInstrumentRepository;

    @Autowired
    private ModelRepository modelRepository;

    public Transaction getTransaction(long transactionId, long userId) {
        return transactionsRepository.findByIdAndUserIdEquals(transactionId, userId);
    }

    public Iterable<Transaction> getAllTransactions(long userId) {
        return transactionsRepository.findByStornoIDIsNullAndUserIdEquals(userId);
    }

    public Iterable<Transaction> getCompletedTransactions(long userId) {
        return transactionsRepository.findByCanceledIsFalseAndUserIdEquals(userId);
    }

    public Iterable<Transaction> getCanceledTransactions(long userId) {
        return transactionsRepository.findByCanceledIsTrueAndUserIdEquals(userId);
    }

    public TransactionForDetailedDto createTransaction(TransactionToCreateDto transactionToCreateDto, long userId) {
        Transaction newTransaction = new Transaction();
        newTransaction.setNumber(getNextTransactionNumber());
        newTransaction.setDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        newTransaction.setDescription(transactionToCreateDto.getDescription());
        newTransaction.setStatusId(statusRepository.findById(Status.Finalized.getValue()));
        newTransaction.setTransactionTypeId(getTransactionType(transactionToCreateDto));
        newTransaction.setPaymentInstrumentId(paymentInstrumentRepository.findById(transactionToCreateDto.getPaymentInstrumentId()));
        newTransaction.setPayerIBAN(transactionToCreateDto.getPayerIBAN());
        newTransaction.setReceiverIBAN(transactionToCreateDto.getReceiverIBAN());
        newTransaction.setTransactionAmount(transactionToCreateDto.getTransactionAmount());
        newTransaction.setPayerCurrency(getCurrencyFromIBAN(transactionToCreateDto.getPayerIBAN()));
        newTransaction.setReceiverCurrency(getCurrencyFromIBAN(transactionToCreateDto.getReceiverIBAN()));
        newTransaction.setReceiverExchangeRate(getBankExchangeRate(getBankUid(transactionToCreateDto.getReceiverIBAN())));
        newTransaction.setSwiftCode(transactionToCreateDto.getSwiftCode());
        newTransaction.setModelId(modelRepository.findById(transactionToCreateDto.getModelId()));
        newTransaction.setReferenceNumber(transactionToCreateDto.getReferenceNumber());
        newTransaction.setUsageCode(transactionToCreateDto.getUsageCode());
        newTransaction.setUid(Integer.toString(new Random().nextInt(10000000)));
        newTransaction.setCanceled(false);
        newTransaction.setUserId(userId);
        newTransaction.setTypeOfExpense(getExpenseTypeFromAccountMicroservice(transactionToCreateDto.getReceiverIBAN()));
        transactionsRepository.save(newTransaction);
        return mapToDetailedTrasanction(newTransaction);
    }

    private String getNextTransactionNumber() {
        return Integer.toString(new Random().nextInt(10000000));
    }

    private String getCurrencyFromIBAN(String IBAN) {
        return "HRK";
    }

    private String getBankUid(String IBAN) {
        //mora vracat osam brojeva - oznaka banke iz IBAN
        return "01234567";
    }

    private float getBankExchangeRate(String bankUid) {
        return (float)0.0;
    }

    private String getExpenseTypeFromAccountMicroservice(String receiverIBAN) {
        return "Food";
    }

    public void cancelTransaction(String uid, long userId) throws Exception {
        Transaction transaction = transactionsRepository.findByUid(uid);
        Transaction newTransaction = new Transaction();
        newTransaction.setPayerIBAN(transaction.getReceiverIBAN());
        newTransaction.setReceiverIBAN(transaction.getPayerIBAN());
        newTransaction.setStornoID(transaction);
        newTransaction.setUserId(userId);
        transaction.setStatusId(statusRepository.findById(Status.Canceled.getValue()));
        transaction.setCanceled(true);

        transactionsRepository.save(newTransaction);
    }

    public oss.transaction.Dal.Models.TransactionType getTransactionType(TransactionToCreateDto transactionToCreateDto) {
        if(transactionToCreateDto.getPayerIBAN().substring(0,2).equals(transactionToCreateDto.getReceiverIBAN().substring(0,2))) {
            if(transactionToCreateDto.getPayerIBAN().substring(4,11).equals(transactionToCreateDto.getReceiverIBAN().substring(4,11))) {
                return transactionTypeRepository.findById(TransactionType.Internal.getValue());
            }
            else {
                return transactionTypeRepository.findById(TransactionType.National.getValue());
            }
        }
        else
            return transactionTypeRepository.findById(TransactionType.International.getValue());
    }

    public TransactionForDetailedDto mapToDetailedTrasanction(Transaction transaction) {
        TransactionForDetailedDto transactionForDetailed = new TransactionForDetailedDto();
        transactionForDetailed.setId(transaction.getId());
        transactionForDetailed.setUid(transaction.getUid());
        transactionForDetailed.setNumber(transaction.getNumber());
        transactionForDetailed.setDate(transaction.getDate());
        transactionForDetailed.setDescription(transaction.getDescription());
        transactionForDetailed.setStatus(transaction.getStatusId().getName());
        transactionForDetailed.setTransactionType(transaction.getTransactionTypeId().getName());
        transactionForDetailed.setPaymentInstrument(transaction.getTrInstrumentId().getName());
        transactionForDetailed.setTransactionAmount(transaction.getTransactionAmount());
        transactionForDetailed.setPayerIBAN(transaction.getPayerIBAN());
        transactionForDetailed.setPayerCurrency(transaction.getPayerCurrency());
        transactionForDetailed.setReceiverIBAN(transaction.getReceiverIBAN());
        transactionForDetailed.setReceiverCurrency(transaction.getReceiverCurrency());
        transactionForDetailed.setReceiverExchangeRate(transaction.getReceiverExchangeRate());
        transactionForDetailed.setSwiftCode(transaction.getSwiftCode());
        transactionForDetailed.setModel(transaction.getModelId() != null ? transaction.getModelId().getName() : "");
        transactionForDetailed.setReferenceNumber(transaction.getReferenceNumber());
        transactionForDetailed.setUsageCode(transaction.getUsageCode());
        transactionForDetailed.setCanceled(transaction.getCanceled());
        transactionForDetailed.setTypeOfExpense(transaction.getTypeOfExpense());
        return transactionForDetailed;
    }

    public Iterable<TransactionForListDto> mapToListTrasanction(Iterable<Transaction> transactions) {
        ArrayList<TransactionForListDto> transactionsForListDto = new ArrayList<TransactionForListDto>();
        for(Transaction transaction: transactions) {
            TransactionForListDto transactionForListDto = new TransactionForListDto();
            transactionForListDto.setId(transaction.getId());
            transactionForListDto.setUid(transaction.getUid());
            transactionForListDto.setDate(transaction.getDate());
            transactionForListDto.setDescription(transaction.getDescription());
            transactionForListDto.setPayerCurrency(transaction.getPayerCurrency());
            transactionForListDto.setReceiverExchangeRate(transaction.getReceiverExchangeRate());
            transactionForListDto.setTransactionAmount(transaction.getTransactionAmount());
            transactionForListDto.setTypeOfExpense(transaction.getTypeOfExpense());
            transactionsForListDto.add(transactionForListDto);
        }
        return transactionsForListDto;
    }

}
