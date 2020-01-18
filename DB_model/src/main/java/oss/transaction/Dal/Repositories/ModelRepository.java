package oss.transaction.Dal.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import oss.transaction.Dal.Models.Model;
import oss.transaction.Dal.Models.PaymentInstrument;

@Repository
public interface ModelRepository extends CrudRepository<Model,Long> {
    Model findById(long id);
}

