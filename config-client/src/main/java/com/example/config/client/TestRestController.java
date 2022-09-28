package com.example.config.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestController {

    @Value("${foo}")
    private String foo;

    @Value("${my.prop}")
    private String myProp;

    @GetMapping("/")
    public String home() {
        return "Hello World!" + myProp + "," + foo;
    }

}
