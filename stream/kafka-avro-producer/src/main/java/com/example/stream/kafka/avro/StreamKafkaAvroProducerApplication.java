package com.example.stream.kafka.avro;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class StreamKafkaAvroProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StreamKafkaAvroProducerApplication.class, args);
    }

}
