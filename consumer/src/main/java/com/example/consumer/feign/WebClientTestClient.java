package com.example.consumer.feign;

import com.example.consumer.feign.exception.RestApiClientException;
import com.example.consumer.feign.exception.RestApiServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * WebClient version of FeignTestClient
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class WebClientTestClient {
    private final WebClient.Builder webClientBuilder;

    @Value("${provider.url}")
    private String providerUrl;

    private void setHeaders(HttpHeaders headers) {
        log.debug("Insert headers");
        headers.set("authorization", "ey235ntjengje");
    }

    public String getTest(String apiKey) throws RestApiServerException, RestApiClientException {
        try {
            final String url = "/test";
            return getWebClient().get().uri(url)
                    .headers(this::setHeaders)
                    .header("x-api-key", apiKey)
                    .retrieve()
                    .bodyToMono(String.class).block();
        } catch (HttpClientErrorException e) {
            throw new RestApiClientException();
        } catch (Exception e) {
            log.error("", e);
            throw new RestApiServerException();
        }
    }

    public String try4xx() throws RestApiServerException, RestApiClientException {
        try {
            final String url = providerUrl + "/4xx";
            Mono<String> response = getWebClient().get().uri(url)
                    .headers(this::setHeaders)
                    .retrieve()
                    .bodyToMono(String.class);
            log.debug("Request to {}, response body: {}", url, response.block());
            return response.block();
        } catch (HttpClientErrorException e) {
            throw new RestApiClientException();
        } catch (Exception e) {
            throw new RestApiServerException();
        }
    }

    public String try5xx() throws RestApiServerException, RestApiClientException {
        try {
            final String url = providerUrl + "/5xx";
            Mono<String> response = getWebClient().get().uri(url)
                    .headers(this::setHeaders)
                    .retrieve()
                    .bodyToMono(String.class);
            log.debug("Request to {}, response body: {}", url, response.block());
            return response.block();
        } catch (HttpClientErrorException e) {
            throw new RestApiClientException();
        } catch (Exception e) {
            throw new RestApiServerException();
        }
    }

    private WebClient getWebClient() {
        return webClientBuilder
                .baseUrl(providerUrl)
                .build();
    }

}
