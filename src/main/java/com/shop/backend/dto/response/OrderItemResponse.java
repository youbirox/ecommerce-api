package com.shop.backend.dto.response;

import lombok.Data;

@Data
public class OrderItemResponse {

    private Long productId;

    private String productName;

    private Integer quantity;

    private Double price;

}
