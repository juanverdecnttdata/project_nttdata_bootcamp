package com.nttdata.controller;

import com.nttdata.entity.Client;
import com.nttdata.entity.Message;
import com.nttdata.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Predicate;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/all")
    public ResponseEntity<List<Client>> listPersons(){
        List<Client> clients = clientService.getAll();
        return ResponseEntity.ok(clients);
    }

    @PostMapping("/save")
    public ResponseEntity<Client> saveClient(@RequestBody Client client){
        Message message = new Message();
        Client newClient = new Client();
        newClient = clientService.save(client);
        return ResponseEntity.ok(newClient);
    }

    @GetMapping("/getClientById/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable("id") Long id){
        Client client = clientService.getClientById(id);
        return ResponseEntity.ok(client);
    }
}
