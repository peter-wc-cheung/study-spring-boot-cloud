package com.example.consumer.hystrix;

import com.example.consumer.hystrix.feign.HystrixFeignTestClient;
import com.netflix.hystrix.exception.HystrixRuntimeException;
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
public class HystrixFeignTestClientTest {

    @Autowired
    private HystrixFeignTestClient testClient;

    @Test
    void getTest() {
        String response = this.testClient.getTest("abc123");

        assertThat(response).isEqualTo("This is a return from fallback factory");
    }

    @Test
    void getTestWith4xx() {
        stubFor(get("/test").willReturn(status(404)));
        String response = this.testClient.getTest("failed-request-apikey");

        assertThat(response).isEqualTo("This is a return from fallback factory");
    }

    @Test
    void getTestWith5xx() {
        stubFor(get("/test").willReturn(status(500)));
        String response = this.testClient.getTest("failed-request-apikey");

        assertThat(response).isEqualTo("This is a return from fallback factory");
    }

    @Test
    void try4xx() {
        Assertions.assertThrows(HystrixRuntimeException.class, () -> {
            this.testClient.try4xx();
        });
    }

    @Test
    void try5xx() {
        String response = this.testClient.try5xx();

        assertThat(response).isEqualTo("This is a return from fallback factory - 5xx");
    }
}
