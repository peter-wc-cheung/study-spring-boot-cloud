package com.example.provider.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class TestRestController {

    @Value("${response.message.test}")
    private String responseMessageTest;

    @Value("${response.message.x4xx}")
    private String responseMessage4xx;

    @Value("${response.message.x5xx}")
    private String responseMessage5xx;

    @GetMapping("test")
    public ResponseEntity<?> get(HttpServletRequest request, @RequestParam(name = "test", required = false) String test) {

        request.getHeaderNames().asIterator().forEachRemaining(e -> {
            log.info("{}: {}", e, request.getHeader(e));
        });

        String responseMessageTest = this.responseMessageTest;
        if (StringUtils.isNotBlank(test)){
            responseMessageTest += ", param test is " + test;
        }

        return ResponseEntity.ok(responseMessageTest);
    }


    @GetMapping("4xx")
    public ResponseEntity<?> return4xx() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseMessage4xx);
    }

    @GetMapping("5xx")
    public ResponseEntity<?> return5xx() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage5xx);
    }
}
