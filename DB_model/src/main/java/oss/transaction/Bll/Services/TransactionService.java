package oss.transaction.Bll.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import oss.transaction.Bll.Dtos.TransactionToCreateDto;
import oss.transaction.Bll.Enums.PaymentType;
import oss.transaction.Bll.Enums.Status;
import oss.transaction.Dal.Models.Transaction;
import oss.transaction.Dal.Repositories.TransactionsRepository;
import oss.transaction.Bll.Interfaces.ITransactionService;

import java.util.Calendar;

import java.util.Random;

@Service
public class TransactionService implements ITransactionService {

    @Autowired
    private TransactionsRepository transactionsRepository;

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

    public Transaction createTransaction(TransactionToCreateDto transactionToCreateDto, long userId) {
        Transaction newTransaction = new Transaction();
        newTransaction.setNumber(getNextTransactionNumber());
        newTransaction.setDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        newTransaction.setDescription(transactionToCreateDto.getDescription());
        // TODO: how to parse enums?
        //newTransaction.setStatusId(Status.Finalized.getValue());
        //newTransaction.setpaymentType();
        //newTransaction.setPaymentInstrumentId();
        newTransaction.setPayerIBAN(transactionToCreateDto.getPayerIBAN());
        newTransaction.setReceiverIBAN(transactionToCreateDto.getReceiverIBAN());
        newTransaction.setTransactionAmount(transactionToCreateDto.getTransactionAmount());
        newTransaction.setPayerCurrency(getCurrencyFromIBAN(transactionToCreateDto.getPayerIBAN()));
        newTransaction.setReceiverCurrency(getCurrencyFromIBAN(transactionToCreateDto.getReceiverIBAN()));
        newTransaction.setReceiverExchangeRate(getBankExchangeRate(getBankUid(transactionToCreateDto.getReceiverIBAN())));
        newTransaction.setSwiftCode(transactionToCreateDto.getSwiftCode());
        //newTransaction.setModelId(transactionToCreateDto.getModelId());
        //newTransaction.getReferenceNumber(transactionToCreateDto.getReferenceNumber());
        //newTransaction.setUserId(transactionToCreateDto.getUsageCode());
        newTransaction.setUid(Integer.toString(new Random().nextInt(10000000)));
        newTransaction.setCanceled(false);
        newTransaction.setUserId(userId);
        transactionsRepository.save(newTransaction);
        return newTransaction;
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

    public void cancelTransaction(String uid, long userId) throws Exception {
        Transaction transaction = transactionsRepository.findByUid(uid);
        if(transaction == null) {
            throw new Exception("Wrong uid sent!");
        }
        else if(transaction.getCanceled()) {
            throw new Exception("Transaction is already canceled!");
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setPayerIBAN(transaction.getReceiverIBAN());
        newTransaction.setReceiverIBAN(transaction.getPayerIBAN());
        newTransaction.setStornoID(transaction);
        newTransaction.setUserId(userId);
        transaction.setCanceled(true);

        transactionsRepository.save(newTransaction);
    }

}
