package oss.transaction.Dal.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import oss.transaction.Dal.Models.Transaction;
import oss.transaction.Dal.Models.TransactionType;

@Repository
public interface TransactionTypeRepository extends CrudRepository<TransactionType,Long> {
    TransactionType findById(long id);
}
