package com.nttdata.feignclient;

import com.nttdata.model.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="client-service",path = "client", url = "http://localhost:8086")
public interface ClientFeignClient {
    @GetMapping("getClientById/{id}")
    public Client getClientById(@RequestParam("id") Long id);
}
