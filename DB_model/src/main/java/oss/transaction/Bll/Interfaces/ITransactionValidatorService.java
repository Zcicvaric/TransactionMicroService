package oss.transaction.Bll.Interfaces;

import oss.transaction.Bll.Dtos.TransactionToCancelDto;
import oss.transaction.Bll.Dtos.TransactionToCreateDto;

public interface ITransactionValidatorService {
    public void validateTransaction(TransactionToCreateDto transaction) throws Exception;
    public void validateTransactionBeforeCancelation(TransactionToCancelDto transactionToCancel, long userId) throws Exception;
}
