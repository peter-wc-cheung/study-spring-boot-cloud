package com.example.consumer.hystrix.feign;

import com.example.consumer.hystrix.feign.configuration.HystrixFeignClientFallbackFactory;
import com.example.consumer.hystrix.feign.configuration.HystrixFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * A Feign testing client.
 * To ensure fallbackFactory works correctly, enable the circuit breaker by feign.circuitbreaker.enabled=true
 */
@FeignClient(
        name = "service-provider",
        contextId = "hystrix-feign-test-client",
        configuration = HystrixFeignConfiguration.class,
        fallbackFactory = HystrixFeignClientFallbackFactory.class)
public interface HystrixFeignTestClient {

    // Have to confirm 'hystrix.command.default.execution.timeout.enabled' set to true

    @GetMapping("test")
    String getTest(@RequestHeader("x-api-key") String apiKey);

    @GetMapping("4xx")
    String try4xx();

    @GetMapping("5xx")
    String try5xx();





}
