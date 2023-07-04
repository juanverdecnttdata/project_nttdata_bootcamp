package com.nttdata.configuration;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@Configuration
public class ReactiveFeignClientConfig {
    @Bean
    Logger.Level reactiveFeignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
