package com.nttdata.feignclient;

import com.nttdata.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="product-service",path = "product", url = "http://localhost:8084")
public interface ProductFeignClient {
    @GetMapping("getProductById/{id}")
    public Product getProductById(@RequestParam("id") Long id);
}
