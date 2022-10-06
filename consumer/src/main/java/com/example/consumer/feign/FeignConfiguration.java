package com.example.consumer.feign;


import com.example.consumer.feign.exception.RestApiClientException;
import com.example.consumer.feign.exception.RestApiServerException;
import feign.RequestInterceptor;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

@Slf4j
public class FeignConfiguration {

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
        return (s, response) -> {
            String requestUrl = response.request().url();
            Response.Body responseBody = response.body();
            HttpStatus responseStatus = HttpStatus.valueOf(response.status());

            log.info(response.request().httpMethod().name());

            if (responseStatus.is5xxServerError()) {
                return new RestApiServerException(requestUrl, responseBody);
            } else if (responseStatus.is4xxClientError()) {
                return new RestApiClientException(requestUrl, responseBody);
            } else {
                return new Exception("Generic exception");
            }
        };
    }

}
