package oss.transaction.Dal.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import oss.transaction.Dal.Models.Transaction;

import java.rmi.server.UID;

@Repository
public interface TransactionsRepository extends CrudRepository<Transaction,Long> {
    Transaction findById(long id);
    Transaction deleteById(long id);
    Iterable<Transaction> findByReceiverIBAN(String receiverIBAN);
    Transaction findByUid(String uid);
}
