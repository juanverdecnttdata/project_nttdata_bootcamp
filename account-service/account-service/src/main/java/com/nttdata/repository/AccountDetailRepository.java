package com.nttdata.repository;

import com.nttdata.entity.AccountDetail;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.List;
/**
 * Clase repositorio de la entidad AccountDetail
 */
public interface AccountDetailRepository extends ReactiveMongoRepository<AccountDetail, Long> {
}
