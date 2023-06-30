package com.nttdata.repository;

import com.nttdata.entity.ClientProduct;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Clase repositorio de la entidad ClientProduct
 */
public interface ClientProductRepository extends ReactiveMongoRepository<ClientProduct, Long> {
}
