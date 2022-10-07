package com.example.consumer.hystrix.feign.configuration;


import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

/**
 * This is a configuration for Feign with Hystrix.
 */
@Slf4j
public class HystrixFeignConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            log.info("Insert headers");
            requestTemplate.header("authorization", "ey235ntjengje123123");
        };
    }

}
