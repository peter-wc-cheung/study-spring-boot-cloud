package com.example.consumer.feign;

import com.example.consumer.feign.exception.RestApiClientException;
import com.example.consumer.feign.exception.RestApiServerException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWireMock(port = 0, stubs = "classpath:/stubs")
public class FeignTestClientIntgTest {

    @Autowired
    private FeignTestClient testClient;

    @Test
    void getTest() {
        String response = this.testClient.getTest("test-api-key");

        assertThat(response).isEqualTo("Hello world!");
    }

    @Test
    void getTestWith4xx() {
        Assertions.assertThrows(RestApiClientException.class, () -> {
            stubFor(get("/test").willReturn(status(404)));
            this.testClient.getTest("test-api-key");
        });
    }

    @Test
    void getTestWith5xx() {
        Assertions.assertThrows(RestApiServerException.class, () -> {
            stubFor(get("/test").willReturn(status(500)));
            this.testClient.getTest("test-api-key");
        });
    }

    @Test
    void try4xx() {
        Assertions.assertThrows(RestApiClientException.class, () -> {
            this.testClient.try4xx();
        });
    }

    @Test
    void try5xx() {
        Assertions.assertThrows(RestApiServerException.class, () -> {
            this.testClient.try5xx();
        });
    }

}
