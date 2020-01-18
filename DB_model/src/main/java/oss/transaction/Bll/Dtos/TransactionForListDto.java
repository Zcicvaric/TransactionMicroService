package oss.transaction.Bll.Dtos;

import java.sql.Date;

public class TransactionForListDto {
    private long id;
    private String uid;
    private float transactionAmount;
    private String payerCurrency;
    private float receiverExchangeRate;
    private String description;
    private Date date;
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

    public float getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(float transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getPayerCurrency() {
        return payerCurrency;
    }

    public void setPayerCurrency(String payerCurrency) {
        this.payerCurrency = payerCurrency;
    }

    public float getReceiverExchangeRate() {
        return receiverExchangeRate;
    }

    public void setReceiverExchangeRate(float receiverExchangeRate) {
        this.receiverExchangeRate = receiverExchangeRate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTypeOfExpense() {
        return typeOfExpense;
    }

    public void setTypeOfExpense(String typeOfExpense) {
        this.typeOfExpense = typeOfExpense;
    }
}
