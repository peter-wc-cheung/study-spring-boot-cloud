package com.example.consumer.feign.controller;

import com.example.consumer.feign.FeignTestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("test/feign")
public class FeignRestController {

    private final FeignTestClient feignTestClient;

    @GetMapping("getTest")
    public ResponseEntity<?> getTest() {
        return ResponseEntity.ok(feignTestClient.getTest("a-p-i-k-e-y"));
    }

    @GetMapping("try4xx")
    public ResponseEntity<?> try4xx() {
        return ResponseEntity.ok(feignTestClient.try4xx());
    }

    @GetMapping("try5xx")
    public ResponseEntity<?> try5xx() {
        return ResponseEntity.ok(feignTestClient.try5xx());
    }

}
