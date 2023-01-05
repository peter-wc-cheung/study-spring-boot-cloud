package com.example.stream.kafka.avro;

import com.example.stream.kafka.avro.elastic.repository.OrderRepository;
import com.example.stream.kafka.avro.util.AvroUtils;
import com.example.stream.kafka.vo.Order;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaConsumeService {

    private final OrderRepository orderRepository;

    @Value("${application.elastic.enable:false}")
    private boolean applicationElasticEnable;

    @Bean
    public Consumer<Message<GenericRecord>> consumer() {
        return message -> {
            log.info("Received: {}", message);
            log.info("Schema: {}", message.getPayload().getSchema());
            try {
                Order order = AvroUtils.toObject(message.getPayload(), Order.class);

                if (applicationElasticEnable)
                    orderRepository.insertOrder(order);

                log.info("{}", order);
            } catch (JsonMappingException e) {
                log.error("Error!", e);
                throw new RuntimeException(e);
            } catch (IOException e) {
                log.error("Error!", e);
                throw new RuntimeException(e);
            }
        };
    }

}
