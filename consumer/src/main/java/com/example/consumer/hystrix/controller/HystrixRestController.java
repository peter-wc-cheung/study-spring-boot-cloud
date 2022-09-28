package com.example.consumer.hystrix.controller;

import com.example.consumer.hystrix.feign.HystrixFeignTestClient;
import com.example.consumer.hystrix.service.HystrixTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("test/hystrix")
public class HystrixRestController {

    @Autowired
    private HystrixTestService testService;

    @Autowired
    private HystrixFeignTestClient testClient;

    @GetMapping("timeout")
    public ResponseEntity<?> timeout() {
        return ResponseEntity.ok(testService.getResult());
    }


    @GetMapping("circuit/{id}")
    public ResponseEntity<?> tryCircuit(@PathVariable("id") int id) {
        return ResponseEntity.ok(testService.checkMoreThanZero(id));
    }

    @GetMapping("feign/getTest")
    public ResponseEntity<?> getTest() {
        return ResponseEntity.ok(testClient.getTest("a-p-i-k-e-y"));
    }

    @GetMapping("feign/4xx")
    public ResponseEntity<?> get4xx() {
        return ResponseEntity.ok(testClient.try4xx());
    }

    @GetMapping("feign/5xx")
    public ResponseEntity<?> get5xx() {
        return ResponseEntity.ok(testClient.try5xx());
    }



}
