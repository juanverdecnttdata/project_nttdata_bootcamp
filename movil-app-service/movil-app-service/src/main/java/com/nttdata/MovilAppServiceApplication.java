package com.nttdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MovilAppServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(MovilAppServiceApplication.class, args);
  }

}
