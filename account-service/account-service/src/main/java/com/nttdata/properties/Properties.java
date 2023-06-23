package com.nttdata.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Clase que gestiona los strings ingresados en el archivo properties
 */
@Configuration
@PropertySource("classpath:application.properties")
public class Properties {

    @Autowired
    private Environment env;

    public String getPropertyByName(String name) {
        return env.getProperty("name");
    }
}
