package oss.transaction.Dal.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import oss.transaction.Dal.Models.PaymentInstrument;
import oss.transaction.Dal.Models.TransactionType;

@Repository
public interface PaymentInstrumentRepository extends CrudRepository<PaymentInstrument,Long> {
    PaymentInstrument findById(long id);
}
