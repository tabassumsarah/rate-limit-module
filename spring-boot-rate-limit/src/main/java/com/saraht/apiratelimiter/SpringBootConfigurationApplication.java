package com.saraht.apiratelimiter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringBootConfigurationApplication {
    // Entry point of the application
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication
                .run(SpringBootConfigurationApplication.class, args);
    }
}