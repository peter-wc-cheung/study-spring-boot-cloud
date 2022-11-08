package com.example.stream.kafka.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @JsonProperty(required = true)
    private String name;

    @JsonProperty(required = true)
    private Long amount;

}
