package oss.transaction.Bll.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import oss.transaction.Bll.Dtos.TransactionToCreateDto;
import oss.transaction.Bll.Enums.EPaymentType;
import oss.transaction.Bll.Enums.EStatus;
import oss.transaction.Dal.Models.Transaction;
import oss.transaction.Dal.Repositories.TransactionsRepository;

import java.rmi.server.UID;
import java.util.Date;

@Service
public class TransactionService {

    // TODO: sve metode u camelCase
    // TODO: prebacit dependency injector u konstruktor
    // TODO: napravit interface za service i repo8
    // TODO: paymentType u transactionType
    // TODO: Kako primiti enum u request objekt
    // TODO: Enume rename sa e prefiksom
    // TODO: Napravit custom exception za validaciju
    // TODO: Pronac ispravan format za datum (bez milisekundi)

    @Autowired
    private TransactionsRepository transactionsRepository;

    public Transaction addNewTransaction(TransactionToCreateDto transactionToCreateDto) {
        Transaction newTransaction = new Transaction();
        newTransaction.setPayerIBAN(transactionToCreateDto.getPayerIBAN());
        newTransaction.setReceiverIBAN(transactionToCreateDto.getReceiverIBAN());
        newTransaction.setTransactionAmount(transactionToCreateDto.getTransactionAmount());
        transactionsRepository.save(newTransaction);
        return newTransaction;
    }

    // TODO: wrappat u try catch sve metode
    public void validateTransaction(TransactionToCreateDto transaction) {
        ValidateIban(transaction.getPayerIBAN());
        ValidateIban(transaction.getReceiverIBAN());
        validateArgumentsForPaymentType(getPaymentTypeFromRequest(transaction), transaction);
        if(!hasPayerSufficentFunds()) {
            //throw exception
        }

    }
    // TODO: bacat exception ako ne valja iban
    private void ValidateIban(String iban) {

    }

    private EPaymentType getPaymentTypeFromRequest(TransactionToCreateDto transaction) {
        return EPaymentType.National;
    }

    private void validateArgumentsForPaymentType(EPaymentType EPaymentType, TransactionToCreateDto transaction) {
        if (EPaymentType.getValue() == EPaymentType.Internal.getValue() || EPaymentType.getValue() == EPaymentType.National.getValue()) {
                // Validate model
                // referenceNumber
                // Validate UsageCode
        }
        else if (EPaymentType.getValue() == EPaymentType.International.getValue()) {
            // Validate SWIFT - send req to bank
        }
    }

    private boolean hasPayerSufficentFunds() {
         //poslat req racunima u kojem pitamo ima li platitelj x love na racunu
        return true;
    }

    public void createTransaction(TransactionToCreateDto transactionToCreateDto) {
        //Transaction transaction = new Transaction();

        UID uid = new UID();
        String number = getNextTransactionNumber();
        Date date = new Date(System.currentTimeMillis());
        String description = transactionToCreateDto.getDescription();
        long statusId = EStatus.Finalized.getValue();
        long paymentTypeId = getPaymentTypeFromRequest(transactionToCreateDto).getValue();
        long paymentInstrumentId = transactionToCreateDto.getPaymentInstrumentId();
        float transactionAmount = transactionToCreateDto.getTransactionAmount();
        String payerIBAN = transactionToCreateDto.getPayerIBAN();
        String payerCurrency = getCurrencyFromIBAN(transactionToCreateDto.getPayerIBAN());
        String receiverCurrency = getCurrencyFromIBAN(transactionToCreateDto.getReceiverIBAN());
        Float exchangeRate = getBankExchangeRate(getBankUid(transactionToCreateDto.getReceiverIBAN()));
        String swiftCode = transactionToCreateDto.getSwiftCode();
        long modelId = transactionToCreateDto.getModelId();
        String refenceNumber = transactionToCreateDto.getReferenceNumber();
        int usageCode = transactionToCreateDto.getUsageCode();
    }

    private String getNextTransactionNumber() {
        return "500";
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






}
