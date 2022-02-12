package com.devendrabrain.NotificationManager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@JsonPropertyOrder({"CustomerId","OrderId","TotalPrice","InventoryDetails","CustomerEmail","CustomerFirstName","CustomerLastName"})
public class NotificationDTO {

    @JsonProperty("CustomerId")
    private Long customerId;
    @JsonProperty("OrderId")
    private Long orderId;
    @JsonProperty("TotalPrice")
    private Long totalPrice;
    @JsonProperty("InventoryDetails")
    private Map<Long, String> inventoryDetails = new HashMap<>();
    @JsonProperty("CustomerEmail")
    private String email;
    @JsonProperty("CustomerFirstName")
    private String customerFirstName;
    @JsonProperty("CustomerLastName")
    private String customerLastName;
}
