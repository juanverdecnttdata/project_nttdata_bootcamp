package com.nttdata.feignclient;

import com.nttdata.entity.Constant;
import com.nttdata.model.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * Clase FeingClient que se conecta al microservicio client-service
 */
@FeignClient(name="client-service",path = "/client", url = Constant.urlClient)
public interface ClientFeignClient {
    /**
     * Metodo que se conecta al servicio remoto y obtiene informacion de la entidad Client
     * @param id Identificador de la entidad Client
     * @return retorna un objeto de la entidad Client
     */
    @GetMapping("/getClientById/{id}")
    public Client getClientById(@RequestParam("id") Long id);
}
