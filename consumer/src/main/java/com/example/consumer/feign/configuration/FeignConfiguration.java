package com.example.consumer.feign.configuration;


import com.example.consumer.feign.exception.RestApiClientException;
import com.example.consumer.feign.exception.RestApiServerException;
import feign.Feign;
import feign.RequestInterceptor;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
public class FeignConfiguration {

    // Disable Hystrix for a single Feign client
    // see https://stackoverflow.com/questions/62669138/disable-hystrix-for-a-single-feign-client
    @Bean
    public Feign.Builder feignBuilder() {
        return Feign.builder();
    }

    // Global method to add request header
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            log.info("Insert headers");
            requestTemplate.header("authorization", "ey235ntjengje");
        };
    }

    // Global exception caught
    @Bean
    public ErrorDecoder errorDecoder() {
        return (methodKey, response) -> {
            String requestUrl = response.request().url();
            Response.Body responseBody = response.body();
            HttpStatus responseStatus = HttpStatus.valueOf(response.status());

            String body = "";
            try {
                InputStream is = responseBody.asInputStream();
                body = IOUtils.toString(is, StandardCharsets.UTF_8);
            } catch (IOException ignored) {
            }

            if (responseStatus.is5xxServerError()) {
                return new RestApiServerException(requestUrl, responseBody);
            } else if (responseStatus.is4xxClientError()) {
                return new RestApiClientException("Client 4xx exception", requestUrl, responseStatus, body);
            } else {
                return new Exception("Generic exception");
            }
        };
    }

}
