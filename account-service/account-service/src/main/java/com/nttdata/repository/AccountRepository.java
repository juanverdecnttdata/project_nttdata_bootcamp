package com.nttdata.repository;

import com.nttdata.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Clase repositorio de la entidad Account
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
}
