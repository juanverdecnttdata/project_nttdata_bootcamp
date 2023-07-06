package com.nttdata.repository;

import com.nttdata.entity.ClientProductLog;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Clase repositorio de la entidad ClientProduct
 */
public interface ClientProductLogRepository extends ReactiveMongoRepository<ClientProductLog, Long> {


}
