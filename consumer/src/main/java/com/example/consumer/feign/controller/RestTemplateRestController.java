package com.example.consumer.feign.controller;

import com.example.consumer.feign.exception.RestApiClientException;
import com.example.consumer.feign.exception.RestApiServerException;
import com.example.consumer.feign.RestTemplateTestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("test/rest-client")
public class RestTemplateRestController {

    private final RestTemplateTestClient restTemplateTestClient;

    @GetMapping("getTest")
    public ResponseEntity<?> getTest() throws RestApiServerException, RestApiClientException {
        return ResponseEntity.ok(restTemplateTestClient.getTest("a-p-i-k-e-y"));
    }

    @GetMapping("try4xx")
    public ResponseEntity<?> try4xx() throws RestApiServerException, RestApiClientException {
        return ResponseEntity.ok(restTemplateTestClient.try4xx());
    }

    @GetMapping("try5xx")
    public ResponseEntity<?> try5xx() throws RestApiServerException, RestApiClientException {
        return ResponseEntity.ok(restTemplateTestClient.try5xx());
    }

}
