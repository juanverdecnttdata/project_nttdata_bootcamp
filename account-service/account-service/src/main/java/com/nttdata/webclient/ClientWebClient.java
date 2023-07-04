package com.nttdata.webclient;

import com.nttdata.entity.Constant;
import com.nttdata.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ClientWebClient {
    @Bean
    WebClient localClient() {
        return WebClient.create(Constant.urlClient_webCLient);
    }

    private WebClient webClient;

    @GetMapping("/getClientById/{id}")
    public Mono<Client> getClientById(@RequestParam("id") Long id){
        return webClient
                .get()
                .uri("/getClientById/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Client.class);
    }
}
