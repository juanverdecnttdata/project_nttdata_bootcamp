package com.nttdata.service;

import com.nttdata.entity.Client;
import com.nttdata.entity.Message;
import com.nttdata.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Servicio de la entidad Person para acceder al repository
 */
@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    /**
     * Metodo que busca todos los datos de la entidad Client
     * @return retorna una lista de la entidad Client
     */
    public Flux<Client> getAll(){
        return clientRepository.findAll();
    }
    /**
     * Meotodo que busca una persona
     * @param id Identificador de la entidad Client
     * @return retorna un objeto de la entidad Client
     */
    public Mono<Client> getClientById(Long id){
        return clientRepository.findById(id);
    }
    /**
     * Metodo que realiza la insercion y actualizacion de la entidad Client
     * @param client Objeto de la entidad Client
     * @return retorna el objeto de la entidad Client insertado o actualizado
     */
    public Mono<Client> save(Client client) {
        Message message = new Message();
        Client newClient = new Client();
        client.setId(sequenceGeneratorService.getSequenceNumber(Client.SEQUENCE_NAME));
        try {
            newClient = clientRepository.save(client).toFuture().get();
            message = new Message("001", "Client created");
            newClient.setMessage(message);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return Mono.just(newClient);
    }

}
