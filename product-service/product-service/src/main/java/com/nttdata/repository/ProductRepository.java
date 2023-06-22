package com.nttdata.repository;

import com.nttdata.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Clase repositorio de la entidad Product
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
}
