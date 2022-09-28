package com.example.consumer.feign.exception;

import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
public class RestApiClientException extends Exception {

    private String requestUrl;
    private Response.Body responseBody;

    public RestApiClientException() {
        super("Client exception caught!");
    }

    public RestApiClientException(String requestUrl, Response.Body responseBody) {
        super("Client exception caught!");
        log.info("Request URL: {}", requestUrl);
        log.info("Response: {}", responseBody);

        try {
            InputStream is = responseBody.asInputStream();
            log.info(IOUtils.toString(is, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.requestUrl = requestUrl;
        this.responseBody = responseBody;
    }

}
