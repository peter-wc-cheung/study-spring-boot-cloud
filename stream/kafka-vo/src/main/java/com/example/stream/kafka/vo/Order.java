package com.example.stream.kafka.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @JsonProperty(required = true)
    private UUID orderId;

    @JsonProperty(required = true)
    private String name;

    @JsonProperty(required = true)
    private String store;

    @JsonProperty(required = true)
    private List<OrderItem> orderItem;

}
