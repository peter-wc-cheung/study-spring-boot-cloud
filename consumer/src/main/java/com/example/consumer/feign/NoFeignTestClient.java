package com.example.consumer.feign;

import com.example.consumer.feign.exception.RestApiClientException;
import com.example.consumer.feign.exception.RestApiServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private static final String REST_URL_PROVIDER_PREFIX = "http://service-provider";

    public String getTest(String apiKey) throws RestApiServerException, RestApiClientException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "ey235ntjengje");
        headers.set("x-api-key", apiKey);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(REST_URL_PROVIDER_PREFIX + "/test", HttpMethod.GET,
                    requestEntity, String.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new RestApiClientException();
        } catch (Exception e) {
            log.error("", e);
            throw new RestApiServerException();
        }
    }

    public String try4xx() throws RestApiServerException, RestApiClientException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "ey235ntjengje");

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(REST_URL_PROVIDER_PREFIX + "/4xx", HttpMethod.GET,
                    requestEntity, String.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new RestApiClientException();
        } catch (Exception e) {
            throw new RestApiServerException();
        }
    }

    public String try5xx() throws RestApiServerException, RestApiClientException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "ey235ntjengje");

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(REST_URL_PROVIDER_PREFIX + "/5xx", HttpMethod.GET,
                    requestEntity, String.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new RestApiClientException();
        } catch (Exception e) {
            throw new RestApiServerException();
        }
    }

}
