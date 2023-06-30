package com.nttdata.repository;

import com.nttdata.entity.AccountHistory;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Clase repositorio de la entidad AccountHistory
 */
public interface AccountHistoryRepository extends ReactiveMongoRepository<AccountHistory, Long> {
}
