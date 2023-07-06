package com.nttdata.repository;

import com.nttdata.entity.Account;
import com.nttdata.entity.AccountLog;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Clase repositorio de la entidad Account
 */
public interface AccountLogRepository extends ReactiveMongoRepository<AccountLog, Long> {


}
