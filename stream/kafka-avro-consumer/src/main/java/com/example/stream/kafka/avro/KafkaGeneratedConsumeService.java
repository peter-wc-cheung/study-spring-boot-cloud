package com.example.stream.kafka.avro;

import com.example.stream.kafka.avro.elastic.repository.GeneratedOrderRepository;
import com.example.stream.kafka.avro.generated.GeneratedOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaGeneratedConsumeService {

    private final GeneratedOrderRepository orderRepository;

    @Value("${application.elastic.enable:false}")
    private boolean applicationElasticEnable;

    @Bean
    public Consumer<Message<GeneratedOrder>> generatedConsumer() {
        return message -> {
            log.info("Received: {}", message);
            log.info("Schema: {}", message.getPayload().getSchema());
            try {

                if (applicationElasticEnable)
                    orderRepository.insertOrder(message.getPayload());

                log.info("{}", message.getPayload());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

}
