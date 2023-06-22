package com.nttdata.repository;

import com.nttdata.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Clase repositorio de la entidad Client
 */
public interface ClientRepository extends JpaRepository<Client, Long> {
}
