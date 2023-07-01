package com.nttdata.controller;

import com.nttdata.entity.Client;
import com.nttdata.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Flux<Client> listClients(){
        return clientService.getAll();
    }
    /**
     * Metodo que realiza la insercion y actualizacion de la entidad Client
     * @param client Objeto de la entidad Client
     * @return retorna el objeto de la entidad Client insertado o actualizado
     */
    @PostMapping("/save")
    public Mono<Client> saveClient(@RequestBody Client client){
        return clientService.save(client);
    }
    /**
     * Metodo que busca una persona
     * @param id Identificador de la entidad Client
     * @return retorna un objeto de la entidad Client
     */
    @GetMapping("/getClientById/{id}")
    public Mono<Client> getClientById(@PathVariable("id") Long id){
        System.out.println("id " + id);
        return clientService.getClientById(id);
    }
}
