package com.nttdata.service;

import com.nttdata.entity.Client;
import com.nttdata.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Servicio de la entidad Person para acceder al repository
 */
@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    /**
     * Metodo que busca todos los datos de la entidad Client
     * @return retorna una lista de la entidad Client
     */
    public List<Client> getAll(){
        return clientRepository.findAll();
    }
    /**
     * Meotodo que busca una persona
     * @param id Identificador de la entidad Client
     * @return retorna un objeto de la entidad Client
     */
    public Client getClientById(Long id){
        return clientRepository.findById(id).orElse(null);
    }
    /**
     * Metodo que realiza la insercion y actualizacion de la entidad Client
     * @param client Objeto de la entidad Client
     * @return retorna el objeto de la entidad Client insertado o actualizado
     */
    public Client save(Client client) {
        return clientRepository.save(client);
    }

}
