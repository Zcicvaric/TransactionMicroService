package oss.transaction.Bll.Dtos;

import oss.transaction.Dal.Models.*;

import javax.persistence.*;
import java.sql.Date;

public class TransactionForDetailedDto {
    private long id;
    private String uid;
    private String number;
    private Date date;
    private String description;
    private String status;
    private String transactionType;
    private String paymentInstrument;
    private float transactionAmount;
    private String payerIBAN;
    private String payerCurrency;
    private String receiverIBAN;
    private String receiverCurrency;
    private float receiverExchangeRate;
    private String swiftCode;
    private String model;
    private String referenceNumber;
    private String usageCode;
    private Boolean canceled;
    private String typeOfExpense;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getPaymentInstrument() {
        return paymentInstrument;
    }

    public void setPaymentInstrument(String paymentInstrument) {
        this.paymentInstrument = paymentInstrument;
    }

    public float getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(float transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getPayerIBAN() {
        return payerIBAN;
    }

    public void setPayerIBAN(String payerIBAN) {
        this.payerIBAN = payerIBAN;
    }

    public String getPayerCurrency() {
        return payerCurrency;
    }

    public void setPayerCurrency(String payerCurrency) {
        this.payerCurrency = payerCurrency;
    }

    public String getReceiverIBAN() {
        return receiverIBAN;
    }

    public void setReceiverIBAN(String receiverIBAN) {
        this.receiverIBAN = receiverIBAN;
    }

    public String getReceiverCurrency() {
        return receiverCurrency;
    }

    public void setReceiverCurrency(String receiverCurrency) {
        this.receiverCurrency = receiverCurrency;
    }

    public float getReceiverExchangeRate() {
        return receiverExchangeRate;
    }

    public void setReceiverExchangeRate(float receiverExchangeRate) {
        this.receiverExchangeRate = receiverExchangeRate;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getUsageCode() {
        return usageCode;
    }

    public void setUsageCode(String usageCode) {
        this.usageCode = usageCode;
    }

    public Boolean getCanceled() {
        return canceled;
    }

    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }

    public String getTypeOfExpense() {
        return typeOfExpense;
    }

    public void setTypeOfExpense(String typeOfExpense) {
        this.typeOfExpense = typeOfExpense;
    }
}
