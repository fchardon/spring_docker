package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource({
        "classpath:config.properties"
})
public class AppConfig {
    @Autowired
    public Environment env;

    public String getVersion(){
        return env.getProperty("appversion");
    }

    public String getEnv(){
        return env.getProperty("servername");
    }
}
