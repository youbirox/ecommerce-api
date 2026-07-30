package com.shop.backend.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {

    private List<OrderItemRequest> items;

}
