package com.shop.backend.dto.response;

import com.shop.backend.entity.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {

    private Long id;

    private OrderStatus status;

    private Double totalPrice;

    private LocalDateTime createdAt;

    private List<OrderItemResponse> items;

}
