package com.example.consumer.feign.controller;

import com.example.consumer.feign.exception.RestApiClientException;
import com.example.consumer.feign.exception.RestApiServerException;
import com.example.consumer.feign.NoFeignTestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("test/no-feign")
public class NoFeignRestController {

    private final NoFeignTestClient noFeignTestClient;

    @GetMapping("getTest")
    public ResponseEntity<?> getTest() throws RestApiServerException, RestApiClientException {
        return ResponseEntity.ok(noFeignTestClient.getTest("a-p-i-k-e-y"));
    }

    @GetMapping("try4xx")
    public ResponseEntity<?> try4xx() throws RestApiServerException, RestApiClientException {
        return ResponseEntity.ok(noFeignTestClient.try4xx());
    }

    @GetMapping("try5xx")
    public ResponseEntity<?> try5xx() throws RestApiServerException, RestApiClientException {
        return ResponseEntity.ok(noFeignTestClient.try5xx());
    }

}
