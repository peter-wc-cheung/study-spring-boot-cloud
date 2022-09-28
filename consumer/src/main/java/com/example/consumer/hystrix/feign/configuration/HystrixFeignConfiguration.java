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

//    @Bean
//    public ErrorDecoder errorDecoder() {
//        return (s, response) -> {
//            String requestUrl = response.request().url();
//            Response.Body responseBody = response.body();
//            HttpStatus responseStatus = HttpStatus.valueOf(response.status());
//
//            log.info(response.request().httpMethod().name());
//
//            if (responseStatus.is5xxServerError()) {
//                return new RestApiServerException(requestUrl, responseBody);
//            } else if (responseStatus.is4xxClientError()) {
//                return new RestApiClientException(requestUrl, responseBody);
//            } else {
//                return new Exception("Generic exception");
//            }
//        };
//    }

}
