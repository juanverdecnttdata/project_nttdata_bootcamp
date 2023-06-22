package com.nttdata.controller;

import com.nttdata.entity.Client;
import com.nttdata.entity.Message;
import com.nttdata.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Clase Controller de la entidad Person
 */
@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientService clientService;
    /**
     * Metodo que busca todos los datos de la entidad Client
     * @return retorna una lista de la entidad Client
     */
    @GetMapping("/all")
    public ResponseEntity<List<Client>> listClients(){
        List<Client> clients = clientService.getAll();
        return ResponseEntity.ok(clients);
    }
    /**
     * Metodo que realiza la insercion y actualizacion de la entidad Client
     * @param client Objeto de la entidad Client
     * @return retorna el objeto de la entidad Client insertado o actualizado
     */
    @PostMapping("/save")
    public ResponseEntity<Client> saveClient(@RequestBody Client client){
        Message message = new Message();
        Client newClient = new Client();
        newClient = clientService.save(client);
        return ResponseEntity.ok(newClient);
    }
    /**
     * Metodo que busca una persona
     * @param id Identificador de la entidad Client
     * @return retorna un objeto de la entidad Client
     */
    @GetMapping("/getClientById/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable("id") Long id){
        Client client = clientService.getClientById(id);
        return ResponseEntity.ok(client);
    }
}
