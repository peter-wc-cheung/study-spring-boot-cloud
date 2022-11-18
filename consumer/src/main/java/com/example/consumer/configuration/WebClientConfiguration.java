package com.example.consumer.configuration;

import io.netty.resolver.DefaultAddressResolverGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;


@Slf4j
@Configuration
public class WebClientConfiguration {

//    @Bean
//    @LoadBalanced
//    public WebClient webClient(){
//        return WebClient.builder().build();
//    }
//
    @Bean
    public HttpClient httpClientReactor() {
        return HttpClient.create()
                .resolver(DefaultAddressResolverGroup.INSTANCE);
    }


    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder(HttpClient httpClient) {
        return WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient));
    }

}
