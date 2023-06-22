package com.nttdata.feignclient;

import com.nttdata.model.Person;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="person-service",path = "person", url = "http://localhost:8083")
public interface PersonFeignClient {
    @GetMapping("getPersonById/{id}")
    public Person getPersonById(@RequestParam("id") Long id);
}
