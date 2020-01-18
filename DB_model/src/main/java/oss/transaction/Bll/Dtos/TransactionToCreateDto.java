package oss.transaction.Bll.Dtos;

public class TransactionToCreateDto {

    private String description;
    private int paymentTypeId;
    private int paymentInstrumentId;
    private float transactionAmount;
    private String payerIBAN;
    private String receiverIBAN;
    private String swiftCode;
    private long modelId;
    private String referenceNumber;
    private String usageCode;
    private String uid;

    public String getDescription() {
        return description;
    }

    public int getPaymentTypeId() {
        return paymentTypeId;
    }

    public int getPaymentInstrumentId() {
        return paymentInstrumentId;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public long getModelId() {
        return modelId;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public String getUsageCode() {
        return usageCode;
    }


    public String getReceiverIBAN() {
        return receiverIBAN;
    }

    public String getPayerIBAN (){
            return payerIBAN;
    }

    public float getTransactionAmount() {
        return transactionAmount;
    }
    public String getUid(){return uid;}
}
