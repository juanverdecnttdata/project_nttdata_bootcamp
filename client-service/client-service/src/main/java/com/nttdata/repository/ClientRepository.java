package com.nttdata.repository;

import com.nttdata.entity.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Clase repositorio de la entidad Client
 */
@Repository
public interface ClientRepository extends ReactiveMongoRepository<Client, Long> {
}
