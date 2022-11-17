package com.example.consumer.feign;

import com.example.consumer.feign.exception.RestApiClientException;
import com.example.consumer.feign.exception.RestApiServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate version of FeignTestClient
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class NoFeignTestClient {

    private final RestTemplate restTemplate;

    @Value("${provider.url}")
    private String providerUrl;

    private HttpHeaders getHeaders() {
        log.debug("Insert headers");
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "ey235ntjengje");
        return headers;
    }

    public String getTest(String apiKey) throws RestApiServerException, RestApiClientException {
        HttpHeaders headers = getHeaders();
        headers.set("x-api-key", apiKey);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            final String url = providerUrl + "/test";
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
            log.debug("Request to {}, response body: {}", url, response.getBody());
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new RestApiClientException();
        } catch (Exception e) {
            log.error("", e);
            throw new RestApiServerException();
        }
    }

    public String try4xx() throws RestApiServerException, RestApiClientException {
        HttpEntity<Void> requestEntity = new HttpEntity<>(getHeaders());

        try {
            final String url = providerUrl + "/4xx";
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
            log.debug("Request to {}, response body: {}", url, response.getBody());
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new RestApiClientException();
        } catch (Exception e) {
            throw new RestApiServerException();
        }
    }

    public String try5xx() throws RestApiServerException, RestApiClientException {
        HttpEntity<Void> requestEntity = new HttpEntity<>(getHeaders());

        try {
            final String url = providerUrl + "/5xx";
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
            log.debug("Request to {}, response body: {}", url, response.getBody());
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new RestApiClientException();
        } catch (Exception e) {
            throw new RestApiServerException();
        }
    }

}
