package com.nttdata.repository;

import com.nttdata.entity.ClientProductUserWallet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Clase repositorio de la entidad ClientProduct
 */

public interface ClientProductUserWalletRepository extends ReactiveMongoRepository<ClientProductUserWallet, Long> {

}
