package com.nttdata.repository;

import com.nttdata.entity.AccountDetail;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.List;
/**
 * Clase repositorio de la entidad AccountDetail
 */
public interface AccountDetailRepository extends ReactiveMongoRepository<AccountDetail, Long> {

    @Query(value = "SELECT ad FROM AccountDetail ad WHERE ad.id_account = id_account")
    List<AccountDetail> findAccountDetailByAccount(Long id_account);
}
