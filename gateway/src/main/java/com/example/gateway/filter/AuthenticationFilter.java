package com.example.gateway.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class AuthenticationFilter implements GatewayFilterFactory<AuthenticationFilter.Config> {

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            log.info("Config: {}", config);

            if (! request.getHeaders().containsKey("x-api-key")) {
                return onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);
            }

            String apiKey = request.getHeaders().getFirst("x-api-key");

            if (apiKey != null && apiKey.equals("api-key")) {
                return chain.filter(exchange);
            }

            return onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
        };
    }

    @Override
    public Class<Config> getConfigClass() {
        return Config.class;
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        log.info("{}", err);
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentLength(err.length());
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);

        DataBuffer dataBuffer = response.bufferFactory().wrap(err.getBytes(StandardCharsets.UTF_8));
        response.getHeaders().setContentLength(err.length());
        response.writeWith(Mono.just(dataBuffer)).subscribe();
        exchange.mutate().response(response).build();

        return response.setComplete();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Config {
        // Default value
        private String name = "AuthenticationFilterFactory";
        private String tempArg1;
    }

}
