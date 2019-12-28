package oss.transaction.Bll.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import oss.transaction.Bll.Dtos.TransactionToCreateDto;
import oss.transaction.Bll.Enums.PaymentType;
import oss.transaction.Bll.Enums.Status;
import oss.transaction.Dal.Models.Transaction;
import oss.transaction.Dal.Repositories.TransactionsRepository;

import java.rmi.server.UID;
import java.util.Date;
import java.util.UUID;

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

    public Transaction AddNewTransaction(TransactionToCreateDto transactionToCreateDto) {
        Transaction newTransaction = new Transaction();
        newTransaction.setPayerIBAN(transactionToCreateDto.getPayerIBAN());
        newTransaction.setReceiverIBAN(transactionToCreateDto.getReceiverIBAN());
        newTransaction.setTransactionAmount(transactionToCreateDto.getTransactionAmount());
        transactionsRepository.save(newTransaction);
        return newTransaction;
    }

    // TODO: wrappat u try catch sve metode
    public void ValidateTransaction(TransactionToCreateDto transaction) {
        ValidateIban(transaction.getPayerIBAN());
        ValidateIban(transaction.getReceiverIBAN());
        ValidateArgumentsForPaymentType(GetPaymentTypeFromRequest(transaction), transaction);
        if(!HasPayerSufficentFunds()) {
            //throw exception
        }

    }
    // TODO: bacat exception ako ne valja iban
    private void ValidateIban(String iban) {

    }

    private PaymentType GetPaymentTypeFromRequest(TransactionToCreateDto transaction) {
        return PaymentType.National;
    }

    private void ValidateArgumentsForPaymentType(PaymentType paymentType, TransactionToCreateDto transaction) {
        if (paymentType.getValue() == PaymentType.Internal.getValue() || paymentType.getValue() == PaymentType.National.getValue()) {
                // Validate model
                // referenceNumber
                // Validate UsageCode
        }
        else if (paymentType.getValue() == PaymentType.International.getValue()) {
            // Validate SWIFT - send req to bank
        }
    }

    private boolean HasPayerSufficentFunds() {
         //poslat req racunima u kojem pitamo ima li platitelj x love na racunu
        return true;
    }

    public void CreateTransaction(TransactionToCreateDto transactionToCreateDto) {
        //Transaction transaction = new Transaction();

        UID uid = new UID();
        String number = getNextTransactionNumber();
        Date date = new Date(System.currentTimeMillis());
        String description = transactionToCreateDto.getDescription();
        long statusId = Status.Finalized.getValue();
        long paymentTypeId = GetPaymentTypeFromRequest(transactionToCreateDto).getValue();
        long paymentInstrumentId = transactionToCreateDto.getPaymentInstrumentId();
        float transactionAmount = transactionToCreateDto.getTransactionAmount();
        String payerIBAN = transactionToCreateDto.getPayerIBAN();
        String payerCurrency = GetCurrencyFromIBAN(transactionToCreateDto.getPayerIBAN());
        String receiverCurrency = GetCurrencyFromIBAN(transactionToCreateDto.getReceiverIBAN());
        Float exchangeRate = GetBankExchangeRate(GetBankUid(transactionToCreateDto.getReceiverIBAN()));
        String swiftCode = transactionToCreateDto.getSwiftCode();
        long modelId = transactionToCreateDto.getModelId();
        String refenceNumber = transactionToCreateDto.getReferenceNumber();
        int usageCode = transactionToCreateDto.getUsageCode();
    }

    private String getNextTransactionNumber() {
        return "500";
    }

    private String GetCurrencyFromIBAN(String IBAN) {
        return "HRK";
    }

    private String GetBankUid(String IBAN) {
        //mora vracat osam brojeva - oznaka banke iz IBAN
        return "01234567";
    }

    private float GetBankExchangeRate(String bankUid) {
        return (float)0.0;
    }






}
