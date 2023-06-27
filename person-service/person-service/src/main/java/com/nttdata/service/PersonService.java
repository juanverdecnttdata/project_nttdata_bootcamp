package com.nttdata.service;

import com.nttdata.entity.Message;
import com.nttdata.entity.Person;
import com.nttdata.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Predicate;

import static com.nttdata.entity.Person.SEQUENCE_NAME;

/**
 * Servicio de la entidad Person para acceder al repository
 */
@Service
public class PersonService {


    @Autowired
    private PersonRepository personRepository;
    /**
     * Metodo que busca todas las personas
     * @return retorna una lista de personas
     */
    public Flux<Person> getAll(){
        return personRepository.findAll();
    }
    /**
     * Meotodo que busca una persona
     * @param id Identificador de la entidad Person
     * @return retorna un objeto de la entidad Person
     */
    public Mono<Person> getPersonById(Long id){
        return personRepository.findById(id).doOnError(null);
    }
    /**
     * Metodo que realiza la insercion y actualizacion de la entidad Person
     * @param person objeto de la entidad Person
     * @return retorna la nueva entidad insertada o actualizada
     */
    public Mono<Person> save(Person person) {
        return personRepository.save(person);
    }

}
