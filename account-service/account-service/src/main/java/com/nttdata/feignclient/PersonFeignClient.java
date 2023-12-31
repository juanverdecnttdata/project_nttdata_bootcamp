package com.nttdata.feignclient;

import com.nttdata.entity.Constant;
import com.nttdata.model.Person;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

/**
 * Clase FeingClient que se conecta al microservicio person-service
 */
@ReactiveFeignClient(name="person-service",path = "person", url = Constant.urlPerson)
public interface PersonFeignClient {
    /**
     * Metodo que se conecta al servicio remoto obtiene la informacion de la entidad Person
     * @param id Identificador de la entidad Person
     * @return retorna un objeto de la entidad Person
     */
    @GetMapping("getPersonById/{id}")
    public Mono<Person> getPersonById(@RequestParam("id") Long id);
}
