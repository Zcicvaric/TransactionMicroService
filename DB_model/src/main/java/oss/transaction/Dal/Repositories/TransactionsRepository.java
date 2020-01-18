package oss.transaction.Dal.Repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import oss.transaction.Dal.Models.Transaction;

import java.rmi.server.UID;

@Repository
public interface TransactionsRepository extends CrudRepository<Transaction,Long> {
    Iterable<Transaction> findByCanceledIsFalseAndUserIdEquals(long userId);
    Iterable<Transaction> findByCanceledIsTrueAndUserIdEquals(long userId);
    Iterable<Transaction> findByStornoIDIsNullAndUserIdEquals(long userId);
    Transaction findByUid(String uid);
    Transaction findByIdAndUserIdEquals(long transactionId, long userId);
}
