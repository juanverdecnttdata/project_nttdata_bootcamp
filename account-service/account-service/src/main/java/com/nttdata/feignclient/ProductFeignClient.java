package com.nttdata.feignclient;

import com.nttdata.entity.Constant;
import com.nttdata.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * Clase FeingClient que se conecta al microservicio product-service
 */
@FeignClient(name="product-service",path = "product", url = Constant.urlProduct)
public interface ProductFeignClient {
    /**
     * Metodo que se conecta al servicio remoto obtiene la informacion de la entidad Product
     * @param id Identificador de la entidad Product
     * @return retorna un objeto de la entidad Product
     */
    @GetMapping("getProductById/{id}")
    public Product getProductById(@RequestParam("id") Long id);
}
