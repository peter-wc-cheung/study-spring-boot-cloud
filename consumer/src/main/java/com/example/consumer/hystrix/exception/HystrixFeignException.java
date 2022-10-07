package com.example.consumer.hystrix.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HystrixFeignException extends RuntimeException {

    public HystrixFeignException() {
        super("Fallback factory!");
    }

}