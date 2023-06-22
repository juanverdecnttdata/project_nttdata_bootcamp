package com.nttdata.repository;

import com.nttdata.entity.ClientProduct;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Clase repositorio de la entidad ClientProduct
 */
public interface ClientProductRepository extends JpaRepository<ClientProduct, Long> {
}
