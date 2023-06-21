package com.nttdata.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:application.properties")
public class Properties {

    @Autowired
    private Environment env;

    public void getPropertyByName(String name) {
        env.getProperty("name)");
    }
}
