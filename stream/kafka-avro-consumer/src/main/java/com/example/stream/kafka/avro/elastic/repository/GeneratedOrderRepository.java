package com.example.stream.kafka.avro.elastic.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.example.stream.kafka.avro.generated.GeneratedOrder;
import com.example.stream.kafka.avro.util.AvroUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

@Slf4j
@RequiredArgsConstructor
@Component
public class GeneratedOrderRepository {

    private final ElasticsearchClient client;
    private final String INDEX_NAME = "generated_order";

    public void insertOrder(GeneratedOrder order) throws IOException {
        log.info("{}", order.toString());
        String json = new String(AvroUtils.convertToJson(order));
        log.info("{}", json);

        Reader reader = new StringReader(json);

        IndexResponse response = client.index(i -> i
                .index(INDEX_NAME)
                .id(order.getOrderId().toString())
                .withJson(reader)
        );

        log.info("Indexed with version " + response.version());
    }

}
