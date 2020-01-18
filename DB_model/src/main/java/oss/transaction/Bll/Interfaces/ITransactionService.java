package oss.transaction.Bll.Interfaces;

import oss.transaction.Bll.Dtos.TransactionForDetailedDto;
import oss.transaction.Bll.Dtos.TransactionForListDto;
import oss.transaction.Bll.Dtos.TransactionToCreateDto;
import oss.transaction.Dal.Models.Transaction;

public interface ITransactionService {
    public Transaction getTransaction(long transactionId, long userId);
    public Iterable<Transaction> getAllTransactions(long userId);
    public Iterable<Transaction> getCompletedTransactions(long userId);
    public Iterable<Transaction> getCanceledTransactions(long userId);
    public TransactionForDetailedDto createTransaction(TransactionToCreateDto transactionToCreateDto, long userId);
    public void cancelTransaction(String uid, long userId) throws Exception;
    public TransactionForDetailedDto mapToDetailedTrasanction(Transaction transaction);
    public Iterable<TransactionForListDto> mapToListTrasanction(Iterable<Transaction> transactions);
}
