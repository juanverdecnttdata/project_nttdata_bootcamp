package com.nttdata.controller;

import com.nttdata.entity.Message;
import com.nttdata.entity.Person;
import com.nttdata.service.PersonService;
import com.nttdata.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;

/**
 * Clase Controller de la entidad Person
 */
@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

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
        Mono<Person> person = personService.getPersonById(id);
        return (person);
    }

    /**
     * Metodo que realiza la insercion y actualizacion de la entidad Person
     *
     * @param person objeto de la entidad Person
     * @return retorna la nueva entidad insertada o actualizada
     */
    @PostMapping("/save")
    public Mono<Person> savePerson(@RequestBody Person person) {
        Message message = new Message();
        Person newPerson = new Person();

        Predicate<Person> isLegalAge = person1 -> person1.getAge() < 18;
        if (isLegalAge.test(person)) {
            message.setCode("999");
            message.setCode("The person needs to have 18 years or more");
            newPerson.setMessage(message);
        } else {

            person.setId(sequenceGeneratorService.getSequenceNumber(Person.SEQUENCE_NAME));
            try {
                newPerson = personService.save(person).toFuture().get();
                message = new Message("001", "Person created");
                newPerson.setMessage(message);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        return Mono.just(newPerson);
    }
}
