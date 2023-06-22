package com.nttdata.repository;

import com.nttdata.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Clase repositorio de la entidad Personn
 */
public interface PersonRepository extends JpaRepository<Person, Long> {
}
