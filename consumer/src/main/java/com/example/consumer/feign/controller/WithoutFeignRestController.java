package com.example.consumer.feign.controller;

import com.example.consumer.feign.exception.RestApiClientException;
import com.example.consumer.feign.exception.RestApiServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("test/no-feign")
public class WithoutFeignRestController {

    private final RestTemplate restTemplate;

    private static final String REST_URL_PROVIDER_PREFIX = "http://SERVICE-PROVIDER";

    @GetMapping("getTest")
    public ResponseEntity<?> getTest() throws RestApiServerException, RestApiClientException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "ey235ntjengje");
        headers.set("x-api-key", "a-p-i-k-e-y");

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(REST_URL_PROVIDER_PREFIX + "/test", HttpMethod.GET,
                    requestEntity, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (HttpClientErrorException e) {
            throw new RestApiClientException();
        } catch (Exception e) {
            log.error("", e);
            throw new RestApiServerException();
        }
    }

    @GetMapping("try4xx")
    public ResponseEntity<?> try4xx() throws RestApiServerException, RestApiClientException {
        HttpEntity<Void> requestEntity = new HttpEntity<>(new HttpHeaders());

        try {
            ResponseEntity<String> response = restTemplate.exchange(REST_URL_PROVIDER_PREFIX + "/4xx", HttpMethod.GET,
                    requestEntity, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (HttpClientErrorException e) {
            throw new RestApiClientException();
        } catch (Exception e) {
            throw new RestApiServerException();
        }
    }

    @GetMapping("try5xx")
    public ResponseEntity<?> try5xx() throws RestApiServerException, RestApiClientException {
        HttpEntity<Void> requestEntity = new HttpEntity<>(new HttpHeaders());

        try {
            ResponseEntity<String> response = restTemplate.exchange(REST_URL_PROVIDER_PREFIX + "/5xx", HttpMethod.GET,
                    requestEntity, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (HttpClientErrorException e) {
            throw new RestApiClientException();
        } catch (Exception e) {
            throw new RestApiServerException();
        }
    }

}
