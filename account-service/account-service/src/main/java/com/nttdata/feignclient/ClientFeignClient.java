package com.nttdata.feignclient;

import com.nttdata.configuration.ReactiveFeignClientConfig;
import com.nttdata.entity.Constant;
import com.nttdata.model.Client;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;


/**
 * Clase FeingClient que se conecta al microservicio client-service
 */
@ReactiveFeignClient(name="client-service",path = "/client", url = Constant.urlClient, configuration = ReactiveFeignClientConfig.class)
public interface ClientFeignClient {
    /**
     * Metodo que se conecta al servicio remoto y obtiene informacion de la entidad Client
     * @param id Identificador de la entidad Client
     * @return retorna un objeto de la entidad Client
     */
    @GetMapping("/getClientById/{id}")
    Mono<Client> getClientById(@RequestParam("id") Long id);
}
