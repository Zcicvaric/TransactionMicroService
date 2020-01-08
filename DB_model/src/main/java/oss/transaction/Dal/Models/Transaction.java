
package oss.transaction.Dal.Models;

import javax.persistence.*;
import java.sql.Date;

@SuppressWarnings("ALL")
@Entity

public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "uid")
    private String uid;

    @Column(name = "userId")
    private long userId;

    @Column(name = "number")
    private String number;

    @Column(name = "date")
    private Date date;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private Status statusId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_type_id")
    private TransactionType transactionTypeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_instrument_id")
    private PaymentInstrument paymentInstrumentId;

    @Column(name = "transaction_amount")
    private float transactionAmount;

    @Column(name = "payer_IBAN")
    private String payerIBAN;

    @Column(name = "payer_currency")
    private String payerCurrency;

    @Column(name = "receiver_IBAN")
    private String receiverIBAN;

    @Column(name = "receiver_currency")
    private String receiverCurrency;

    @Column(name = "receiver_exchange_rate")
    private float receiverExchangeRate;

    @Column(name = "swift_code")
    private String swiftCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id")
    private Model modelId;

    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "usage_code")
    private String usageCode;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "storno_id", nullable = true)
    private Transaction stornoID;




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
    public Long getUserId(){return this.userId;}
    public void setUserId(Long userId){this.userId = userId;}


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

    public Status getStatusId() {
        return statusId;
    }

    public void setStatusId(Status statusId) {
        this.statusId = statusId;
    }

    public TransactionType getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(TransactionType transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public PaymentInstrument getTrInstrumentId() {
        return paymentInstrumentId;
    }

    public void setPaymentInstrumentId(PaymentInstrument paymentInstrumentId) {
        this.paymentInstrumentId = paymentInstrumentId;
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

    public Model getModelId() {
        return modelId;
    }

    public void setModelId(Model modelId) {
        this.modelId = modelId;
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

    public Transaction getStornoID() {
        return stornoID;
    }

    public void setStornoID(Transaction stornoID) {
        this.stornoID = stornoID;
    }
}