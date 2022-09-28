package com.example.consumer.feign;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "service-provider", contextId = "test-client", configuration = FeignConfiguration.class)
public interface FeignTestClient {

    // Disable Hystrix as default fallback action already defined on FeignConfiguration.
    @GetMapping("test")
    @HystrixProperty(name = "hystrix.command.default.execution.timeout.enabled", value = "false")
    String getTest(@RequestHeader("x-api-key") String apiKey);

    @GetMapping("4xx")
    String try4xx();

    @GetMapping("5xx")
    String try5xx();

}
