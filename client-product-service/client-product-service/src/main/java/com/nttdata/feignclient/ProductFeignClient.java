package com.nttdata.feignclient;

import com.nttdata.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Clase FeingClient que se conecta al microservicio product-service
 */
@FeignClient(name = "product-service", path = "product", url = "http://localhost:8084")
public interface ProductFeignClient {
  /**
   * Metodo que busca una entidad de producto
   *
   * @return retorna una entidad producto
   */
  @GetMapping("getProductById/{id}")
  Product getProductById(@RequestParam("id") Long id);
}
