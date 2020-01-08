package oss.transaction.Bll.Interfaces;

import oss.transaction.Bll.Dtos.TransactionToCreateDto;

public interface ITransactionValidatorService {
    public void validateTransaction(TransactionToCreateDto transaction) throws Exception;
}
