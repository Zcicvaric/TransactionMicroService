package oss.transaction.Dal.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import oss.transaction.Dal.Models.Status;
import oss.transaction.Dal.Models.Transaction;

@Repository
public interface StatusRepository extends CrudRepository<Status,Long> {
    Status findById(long id);
}