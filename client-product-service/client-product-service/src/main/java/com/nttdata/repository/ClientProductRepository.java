package com.nttdata.repository;

import com.nttdata.entity.ClientProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientProductRepository extends JpaRepository<ClientProduct, Long> {
}
