package com.nttdata.feignclient;

import com.nttdata.model.AccountHistory;
import com.nttdata.model.ClientProduct;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
/**
 * Clase FeingClient que se conecta al microservicio client-product-service
 */
@FeignClient(name="client-product-service",path = "clientProduct", url = "http://localhost:8087")
public interface ClientProductFeignClient {
    /**
     * Metodo que se conecta al servicio remoto obtiene la informacion de la entidad ClientProduct
     * @param id Identificador de la entidad ClientProduct
     * @return retorna un objeto de la entidad ClientProduct
     */
    @GetMapping("getClientProductById/{id}")
    public ClientProduct getClientProductById(@RequestParam("id") Long id);
    /**
     * Metodo que se conecta al servicio remoto obtiene la informacion de la entidades ClientProduct
     * @return retorna un objeto de la entidad ClientProduct
     */
    @GetMapping("/all")
    public List<ClientProduct> listClientsProducts();
    /**
     * Metodo que se conecta al servicio remoto y guarda la informacion de la entidad ClientProduct
     * @param clientProduct Objeto de la entidad ClientProduct
     * @return retorna un objeto de la entidad ClientProduct
     */
    @PostMapping("/save")
    public ClientProduct save(@RequestBody ClientProduct clientProduct);
}
