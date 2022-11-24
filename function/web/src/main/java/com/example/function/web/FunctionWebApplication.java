package com.example.function.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@Slf4j
@SpringBootApplication
public class FunctionWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(FunctionWebApplication.class, args);
    }

    @Bean
    public Function<String, String> uppercase() {
        return (in) -> {
            log.info("In: {}", in);
            return in.toUpperCase();
        };
    }

    @Bean
    public Function<String, String> lowercase() {
        return (in) -> {
            log.info("In: {}", in);
            return in.toLowerCase();
        };
    }

}
