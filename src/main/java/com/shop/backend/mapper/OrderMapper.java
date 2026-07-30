package com.shop.backend.mapper;

import com.shop.backend.dto.response.OrderItemResponse;
import com.shop.backend.dto.response.OrderResponse;
import com.shop.backend.entity.Order;
import com.shop.backend.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {


    public OrderResponse toResponse(Order order) {

        OrderResponse response = new OrderResponse();

        response.setId(order.getId());
        response.setStatus(order.getStatus());
        response.setTotalPrice(order.getTotalPrice());
        response.setCreatedAt(order.getCreatedAt());

        response.setItems(
                order.getItems()
                        .stream()
                        .map(this::toItemResponse)
                        .collect(Collectors.toList())
        );

        return response;
    }


    private OrderItemResponse toItemResponse(OrderItem item) {

        OrderItemResponse response = new OrderItemResponse();

        response.setProductId(item.getProduct().getId());
        response.setProductName(item.getProduct().getName());
        response.setQuantity(item.getQuantity());
        response.setPrice(item.getPrice());

        return response;
    }
}
