package com.example.stream.kafka.avro;

import com.example.stream.kafka.avro.util.AvroUtils;
import com.example.stream.kafka.vo.Order;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
public class KafkaConsumeService {

    @Bean
    public Consumer<Message<GenericRecord>> consumer() {
        return message -> {
            log.info("Received: {}", message);
            try {
                Order order = AvroUtils.toObject(message.getPayload(), Order.class);
                log.info("{}", order);
            } catch (JsonMappingException e) {
                throw new RuntimeException(e);
            }
        };
    }

}
