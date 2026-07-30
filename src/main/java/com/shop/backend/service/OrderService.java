package com.shop.backend.service;

import com.shop.backend.dto.request.CreateOrderRequest;
import com.shop.backend.dto.request.UpdateOrderStatusRequest;
import com.shop.backend.dto.response.OrderResponse;
import com.shop.backend.entity.*;
import com.shop.backend.exception.InsufficientStockException;
import com.shop.backend.exception.OrderNotFoundException;
import com.shop.backend.exception.ProductNotFoundException;
import com.shop.backend.exception.UserNotFoundException;
import com.shop.backend.mapper.OrderMapper;
import com.shop.backend.repository.OrderRepository;
import com.shop.backend.repository.ProductRepository;
import com.shop.backend.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;


    public OrderService(
            OrderRepository orderRepository,
            ProductRepository productRepository,
            UserRepository userRepository,
            OrderMapper orderMapper
    ) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderMapper = orderMapper;
    }


    @Transactional
    public OrderResponse create(CreateOrderRequest request) {

        Order order = new Order();

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setItems(new ArrayList<>());

        double total = 0;


        for (var itemRequest : request.getItems()) {

            Product product = productRepository
                    .findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"));


            if (product.getStock() < itemRequest.getQuantity()) {
                throw new InsufficientStockException("Not enough stock for product: " + product.getName());
            }


            OrderItem item = new OrderItem();

            item.setProduct(product);
            item.setQuantity(itemRequest.getQuantity());
            item.setPrice(product.getPrice());

            item.setOrder(order);

            product.setStock(
                    product.getStock() - itemRequest.getQuantity()
            );

            order.getItems().add(item);


            total += product.getPrice() * itemRequest.getQuantity();
        }


        order.setTotalPrice(total);


        Order savedOrder = orderRepository.save(order);


        return orderMapper.toResponse(savedOrder);
    }

    public List<OrderResponse> getMyOrders() {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Order> orders = orderRepository.findByUserId(user.getId());

        return orders.stream()
                .map(orderMapper::toResponse)
                .toList();

    }

    public OrderResponse getMyOrderById(Long orderId) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found")
                );

        Order order = orderRepository
                .findByIdAndUserId(orderId, user.getId())
                .orElseThrow(() ->
                        new OrderNotFoundException("Order not found")
                );

        return orderMapper.toResponse(order);
    }

    public OrderResponse updateStatus(
            Long orderId,
            UpdateOrderStatusRequest request
    ) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new OrderNotFoundException("Order not found")
                );

        order.setStatus(request.getStatus());

        Order savedOrder = orderRepository.save(order);

        return orderMapper.toResponse(savedOrder);
    }

}
