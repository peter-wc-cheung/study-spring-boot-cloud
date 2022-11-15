package com.example.stream.kafka.avro;

import com.example.stream.kafka.avro.generated.GeneratedOrder;
import com.example.stream.kafka.avro.generated.GeneratedOrderItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@Slf4j
@Component
@EnableScheduling
public class KafkaGeneratedProduceService {
    @Autowired
    private StreamBridge streamBridge;

    @Scheduled(cron = "*/2 * * * * *")
    public void sendMessage() throws IOException {

        GeneratedOrder order = GeneratedOrder.newBuilder()
                .setOrderId(UUID.randomUUID())
                .setName("Generated Order Name")
                .setStore("Store B")
                .setOrderItem(
                        Arrays.asList(
                                GeneratedOrderItem.newBuilder().setName("Item A").setAmount(1L).build(),
                                GeneratedOrderItem.newBuilder().setName("Item B").setAmount(3L).build()
                        )
                )
                .build();

        streamBridge.send("producer-out-1",
                MessageBuilder
                        .withPayload(order)
//                        .setHeader(KafkaHeaders.MESSAGE_KEY, 999L)
                        .build()
        );

    }

}
