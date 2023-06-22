package com.nttdata.repository;

import com.nttdata.entity.AccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Clase repositorio de la entidad AccountHistory
 */
public interface AccountHistoryRepository extends JpaRepository<AccountHistory, Long> {
}
