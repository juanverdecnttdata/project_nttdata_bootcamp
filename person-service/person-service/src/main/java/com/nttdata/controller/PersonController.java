package com.nttdata.controller;

import com.nttdata.entity.Message;
import com.nttdata.entity.Person;
import com.nttdata.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Predicate;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping("/all")
    public ResponseEntity<List<Person>> listPersons(){
        List<Person> persons = personService.getAll();
        return ResponseEntity.ok(persons);
    }
    @GetMapping("/getPersonById/{id}")
    public ResponseEntity<Person> getClientById(@PathVariable("id") Long id){
        Person person = personService.getPersonById(id);
        return ResponseEntity.ok(person);
    }

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
