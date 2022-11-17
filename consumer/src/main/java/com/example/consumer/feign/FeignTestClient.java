package com.example.consumer.feign;

import com.example.consumer.feign.configuration.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "${provider.url}",
        contextId = "test-client",
        configuration = FeignConfiguration.class)
public interface FeignTestClient {

    @GetMapping("test")
    String getTest(@RequestHeader("x-api-key") String apiKey);

    @GetMapping("4xx")
    String try4xx();

    @GetMapping("5xx")
    String try5xx();

}
