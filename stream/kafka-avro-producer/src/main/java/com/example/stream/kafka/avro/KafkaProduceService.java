package com.example.stream.kafka.avro;

import com.example.stream.kafka.avro.util.AvroUtils;
import com.example.stream.kafka.vo.Order;
import com.example.stream.kafka.vo.OrderItem;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
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
public class KafkaProduceService {

    @Autowired
    private StreamBridge streamBridge;

    @Scheduled(cron = "*/2 * * * * *")
    public void sendMessage() throws IOException {

        Order order = Order.builder()
                .orderId(UUID.randomUUID())
                .name("Name")
                .store("Store A")
                .orderItem(
                        Arrays.asList(
                                OrderItem.builder().name("Item A").amount(1L).build(),
                                OrderItem.builder().name("Item B").amount(3L).build()
                        )
                )
                .build();


        Schema schema = AvroUtils.getSchema(Order.class);
//        log.info("{}", schema.toString(true));

        streamBridge.send("producer-out-0",
                MessageBuilder
                        .withPayload(AvroUtils.toGenericRecord(order))
//                        .setHeader(KafkaHeaders.MESSAGE_KEY, 999L)
                        .build()
        );

//        streamBridge.send("producer-out-0", org.springframework.integration.support.MessageBuilder
//                .withPayload("123")
//                .setHeader(KafkaHeaders.MESSAGE_KEY, 999L).build());
    }
}
