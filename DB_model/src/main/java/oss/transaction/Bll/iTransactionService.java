package oss.transaction.Bll;

import oss.transaction.Bll.Dtos.TransactionToCreateDto;
import oss.transaction.Dal.Models.Transaction;

public interface iTransactionService  {
    public Transaction AddNewTransaction(TransactionToCreateDto transactionToCreateDto);
    public void ValidateTransaction(TransactionToCreateDto transaction);
    public void CreateTransaction(TransactionToCreateDto transactionToCreateDto);
    public Transaction createCancelTransaction(String uid);

}
