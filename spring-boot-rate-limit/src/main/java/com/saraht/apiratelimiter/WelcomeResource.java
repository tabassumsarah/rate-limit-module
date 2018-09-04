package com.saraht.apiratelimiter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    A simple rest resource controller.
 */
@RestController
public class WelcomeResource {

    @Value("${welcome.message}")
    private String welcomeMessage;

    @GetMapping("/welcome")
    public String retrieveWelcomeMessage() {
        return welcomeMessage;
    }

}