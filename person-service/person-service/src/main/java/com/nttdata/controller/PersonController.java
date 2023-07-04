package com.nttdata.controller;

import com.nttdata.entity.Person;
import com.nttdata.service.PersonService;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;


/**
 * Clase Controller de la entidad Person
 */
@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    /**
     * Metodo que busca todas las personas
     *
     * @return retorna una lista de personas
     */
    @GetMapping("/all")
    public Flux<Person> listPersons() {
        return personService.getAll();
    }
    /**
     * Meotodo que busca una persona
     *
     * @param id Identificador de la entidad Person
     * @return retorna un objeto de la entidad Person
     */
    @GetMapping("/getPersonById/{id}")
    public Mono<Person> getPersonById(@PathVariable("id") Long id) {
        System.out.println("id " + id);
        Mono<Person> person = personService.getPersonById(id);
        return person.switchIfEmpty(Mono.just(new Person()));
    }
    /**
     * Metodo que realiza la insercion y actualizacion de la entidad Person
     *
     * @param person objeto de la entidad Person
     * @return retorna la nueva entidad insertada o actualizada
     */
    @PostMapping("/save")
    public Mono<Person> savePerson(@RequestBody Person person) {
        return personService.save(person);
    }
}
