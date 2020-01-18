package oss.transaction.Bll.Interfaces;

import oss.transaction.Bll.Dtos.TransactionToCancelDto;
import oss.transaction.Bll.Dtos.TransactionToCreateDto;

public interface ITransactionValidatorService {
    public void validateTransaction(TransactionToCreateDto transaction) throws Exception;
    public void validateTransactionBeforeCancelation(TransactionToCancelDto transactionToCancel, long userId) throws Exception;
    public void validateIban(String iban) throws Exception;
    public void validateReferenceNumber(String referenceNumber) throws Exception;
    public void validateUsageCode(String usageCode) throws Exception;
    public void validateSWIFT(String swift) throws Exception;
}
