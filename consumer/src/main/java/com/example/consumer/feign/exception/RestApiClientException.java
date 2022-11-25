package com.example.consumer.feign.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;

@Slf4j
public class RestApiClientException extends RuntimeException {

    @Getter
    private String requestUrl;
    @Getter
    private HttpStatusCode status;
    @Getter
    private String response;

    public RestApiClientException(String message) {
        super(message);
    }

    public RestApiClientException(Throwable e, String requestUrl, HttpStatusCode status, String response) {
        super(e);
        this.requestUrl = requestUrl;
        this.status = status;
        this.response = response;
    }

    public RestApiClientException(String message, String requestUrl, HttpStatusCode status, String response) {
        super(message);
        this.requestUrl = requestUrl;
        this.status = status;
        this.response = response;
    }

}
