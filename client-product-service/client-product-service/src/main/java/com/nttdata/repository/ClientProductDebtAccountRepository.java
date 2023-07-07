package com.nttdata.repository;

import com.nttdata.entity.ClientProductDebtAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Clase repositorio de la entidad ClientProduct
 */
public interface ClientProductDebtAccountRepository extends ReactiveMongoRepository<ClientProductDebtAccount, Long> {

}
