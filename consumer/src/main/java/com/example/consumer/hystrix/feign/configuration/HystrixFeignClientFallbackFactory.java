package com.example.consumer.hystrix.feign.configuration;

import com.example.consumer.hystrix.feign.HystrixFeignTestClient;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class HystrixFeignClientFallbackFactory implements FallbackFactory<HystrixFeignTestClient> {

    @PostConstruct
    private void init() {
        log.info("HystrixFeignClientFallbackFactory is now initiation");
    }

    @Override
    public HystrixFeignTestClient create(Throwable cause) {

        log.error("", cause);
        log.error("{}", cause.getMessage());

        String httpStatus = cause instanceof FeignException ? Integer.toString(((FeignException) cause).status()) : "";

        log.info(httpStatus);

        return new HystrixFeignTestClient() {

            @Override
            public String getTest(String apiKey) {
                return "This is a return from fallback factory";
            }

            @Override
            public String try4xx() {
                return "This is a return from fallback factory - 4xx";
            }

            @Override
            public String try5xx() {
                return "This is a return from fallback factory - 5xx";
            }
        };
    }

}
