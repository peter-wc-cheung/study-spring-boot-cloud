package com.example.function.aws.lambda;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@Slf4j
@SpringBootApplication
public class FunctionAwsLambdaApplication {

    public static void main(String[] args) {
        SpringApplication.run(FunctionAwsLambdaApplication.class, args);
    }

    @Bean
    public Function<String, String> uppercase() {
        return (in) -> {
            log.info("In: {}", in);
            return in.toUpperCase();
        };
    }

}
