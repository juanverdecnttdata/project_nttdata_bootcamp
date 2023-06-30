package com.nttdata.repository;

import com.nttdata.entity.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Clase repositorio de la entidad Product
 */
public interface ProductRepository extends ReactiveMongoRepository<Product, Long> {
}
