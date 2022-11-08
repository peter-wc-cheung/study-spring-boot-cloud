package com.example.stream.kafka.avro;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
//@EnableSchemaRegistryClient
public class StreamKafkaAvroProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StreamKafkaAvroProducerApplication.class, args);
    }


//    @Bean
//    public SchemaRegistryClient schemaRegistryClient(@Value("${spring.cloud.stream.schemaRegistryClient.endpoint}") String endpoint){
//        ConfluentSchemaRegistryClient client = new ConfluentSchemaRegistryClient();
//        client.setEndpoint(endpoint);
//        return client;
//    }

}
