package com.nttdata.controller;

import com.nttdata.entity.Message;
import com.nttdata.entity.Person;
import com.nttdata.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Predicate;

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
     * @return retorna una lista de personas
     */
    @GetMapping("/all")
    public ResponseEntity<List<Person>> listPersons(){
        List<Person> persons = personService.getAll();
        return ResponseEntity.ok(persons);
    }

    /**
     * Meotodo que busca una persona
     * @param id Identificador de la entidad Person
     * @return retorna un objeto de la entidad Person
     */
    @GetMapping("/getPersonById/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable("id") Long id){
        Person person = personService.getPersonById(id);
        return ResponseEntity.ok(person);
    }

    /**
     * Metodo que realiza la insercion y actualizacion de la entidad Person
     * @param person objeto de la entidad Person
     * @return retorna la nueva entidad insertada o actualizada
     */
    @PostMapping("/save")
    public ResponseEntity<Person> savePerson(@RequestBody Person person){
        Message message = new Message();
        Person newPerson = new Person();
        Predicate<Person> isLegalAge = person1 -> person1.getAge() < 18;
        if (isLegalAge.test(person)){
            message.setCode("999");
            message.setCode("The person needs to have 18 years or more");
            newPerson.setMessage(message);
            //return newPerson;
        } else {
             message = new Message("001", "Person created");
             newPerson = personService.save(person);
             newPerson.setMessage(message);
        }
        return ResponseEntity.ok(newPerson);
    }
}
