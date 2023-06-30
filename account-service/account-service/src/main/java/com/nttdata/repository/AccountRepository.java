package com.nttdata.repository;

import com.nttdata.entity.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Clase repositorio de la entidad Account
 */
public interface AccountRepository extends ReactiveMongoRepository<Account, Long> {
}
