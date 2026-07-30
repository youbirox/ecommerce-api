package com.shop.backend.controller;

import com.shop.backend.dto.request.CreateOrderRequest;
import com.shop.backend.dto.request.UpdateOrderStatusRequest;
import com.shop.backend.dto.response.OrderResponse;
import com.shop.backend.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;


    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping
    public ResponseEntity<OrderResponse> create(
            @Valid @RequestBody CreateOrderRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.create(request));
    }

    @GetMapping("/my-orders")
    public ResponseEntity<List<OrderResponse>> getMyOrders() {

        return ResponseEntity.ok(
                orderService.getMyOrders()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                orderService.getMyOrderById(id)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateOrderStatusRequest request
    ) {

        return ResponseEntity.ok(
                orderService.updateStatus(id, request)
        );
    }
}
