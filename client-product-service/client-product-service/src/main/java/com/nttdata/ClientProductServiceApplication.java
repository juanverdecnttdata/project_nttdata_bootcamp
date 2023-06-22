package com.nttdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ClientProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientProductServiceApplication.class, args);
	}

}
