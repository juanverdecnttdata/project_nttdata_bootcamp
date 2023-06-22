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

@FeignClient(name="client-product-service",path = "clientProduct", url = "http://localhost:8087")
public interface ClientProductFeignClient {
    @GetMapping("getClientProductById/{id}")
    public ClientProduct getClientProductById(@RequestParam("id") Long id);

    @GetMapping("/all")
    public List<ClientProduct> listClientsProducts();

    @PostMapping("/save")
    public ClientProduct save(@RequestBody ClientProduct clientProduct);
}
