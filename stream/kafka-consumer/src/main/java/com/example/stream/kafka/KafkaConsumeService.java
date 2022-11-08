package com.example.stream.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
public class KafkaConsumeService {
    @Bean
    public Consumer<Message<String>> consumer() {
        return message -> {
            log.info("Received: {}", message);
        };
    }

}
