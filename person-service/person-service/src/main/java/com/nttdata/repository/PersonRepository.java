package com.nttdata.repository;

import com.nttdata.entity.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Clase repositorio de la entidad Personn
 */
@Repository
public interface PersonRepository extends ReactiveMongoRepository<Person, Long> {
}
