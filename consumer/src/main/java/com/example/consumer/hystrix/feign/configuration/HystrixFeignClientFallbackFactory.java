package com.example.consumer.hystrix.feign.configuration;

import com.example.consumer.hystrix.exception.HystrixFeignException;
import com.example.consumer.hystrix.feign.HystrixFeignTestClient;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HystrixFeignClientFallbackFactory implements FallbackFactory<HystrixFeignTestClient> {

    @Override
    public HystrixFeignTestClient create(Throwable cause) {

        String httpStatus = cause instanceof FeignException ? Integer.toString(((FeignException) cause).status()) : "";
        log.info(httpStatus);

        return new HystrixFeignTestClient() {

            // You can also have some hardcoded responses.
            @Override
            public String getTest(String apiKey) {
                log.info("Hello World");
                return "This is a return from fallback factory";
            }

            @Override
            public String try4xx() {
                // Caught Exception by using HystrixRuntimeException.
                // HystrixRuntimeException.getFallbackException() to retrieve Exception details.
                log.info("Hello World");
                throw new HystrixFeignException();
            }

            @Override
            public String try5xx() {
                log.info("Hello World");
                return "This is a return from fallback factory - 5xx";
            }
        };
    }

}
