package com.example.stream.kafka.avro.elastic.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.example.stream.kafka.vo.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderRepository {

    private final ElasticsearchClient client;
    private final String INDEX_NAME = "order";

    public void insertOrder(Order order) throws IOException {

            IndexResponse response = client.index(i -> i
                .index(INDEX_NAME)
                .id(order.getOrderId().toString())
                .document(order)
        );

        log.info("Indexed with version " + response.version());
    }

}
