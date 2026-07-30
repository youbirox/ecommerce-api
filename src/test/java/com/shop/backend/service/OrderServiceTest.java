package com.shop.backend.service;

import com.shop.backend.dto.request.CreateOrderRequest;
import com.shop.backend.dto.request.OrderItemRequest;
import com.shop.backend.dto.response.OrderResponse;
import com.shop.backend.entity.Product;
import com.shop.backend.entity.User;
import com.shop.backend.mapper.OrderMapper;
import com.shop.backend.repository.OrderRepository;
import com.shop.backend.repository.ProductRepository;
import com.shop.backend.repository.UserRepository;
import com.shop.backend.exception.InsufficientStockException;
import com.shop.backend.exception.UserNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class OrderServiceTest {


    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderMapper orderMapper;


    @InjectMocks
    private OrderService orderService;



    @Test
    void shouldCreateOrderSuccessfully() {


        // =========================
        // SecurityContext utilisateur connecté
        // =========================

        Authentication authentication = mock(Authentication.class);

        SecurityContext securityContext = mock(SecurityContext.class);


        when(securityContext.getAuthentication())
                .thenReturn(authentication);


        when(authentication.getName())
                .thenReturn("test@gmail.com");


        SecurityContextHolder.setContext(securityContext);



        // =========================
        // User
        // =========================

        User user = new User();

        user.setId(1L);
        user.setEmail("test@gmail.com");


        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));



        // =========================
        // Product
        // =========================

        Product product = new Product();

        product.setId(2L);
        product.setName("Laptop HP");
        product.setPrice(699.99);
        product.setStock(10);



        when(productRepository.findById(2L))
                .thenReturn(Optional.of(product));



        // =========================
        // Request
        // =========================

        OrderItemRequest itemRequest = new OrderItemRequest();

        itemRequest.setProductId(2L);
        itemRequest.setQuantity(2);



        CreateOrderRequest request = new CreateOrderRequest();

        request.setItems(
                List.of(itemRequest)
        );



        // =========================
        // Save Order
        // =========================

        when(orderRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));



        OrderResponse response = new OrderResponse();

        response.setId(1L);



        when(orderMapper.toResponse(any()))
                .thenReturn(response);



        // =========================
        // Act
        // =========================

        OrderResponse result =
                orderService.create(request);



        // =========================
        // Assert
        // =========================


        assertNotNull(result);

        assertEquals(1L, result.getId());


        // stock diminué
        assertEquals(
                8,
                product.getStock()
        );



        verify(userRepository)
                .findByEmail("test@gmail.com");


        verify(productRepository)
                .findById(2L);


        verify(orderRepository)
                .save(any());


        verify(orderMapper)
                .toResponse(any());

    }

    @Test
    void shouldThrowExceptionWhenStockIsInsufficient() {


        // Arrange

        Authentication authentication = mock(Authentication.class);

        SecurityContext securityContext = mock(SecurityContext.class);


        when(securityContext.getAuthentication())
                .thenReturn(authentication);

        when(authentication.getName())
                .thenReturn("test@gmail.com");


        SecurityContextHolder.setContext(securityContext);



        // User

        User user = new User();

        user.setId(1L);
        user.setEmail("test@gmail.com");


        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));



        // Product avec stock insuffisant

        Product product = new Product();

        product.setId(2L);
        product.setName("Laptop HP");
        product.setPrice(699.99);
        product.setStock(1);



        when(productRepository.findById(2L))
                .thenReturn(Optional.of(product));



        // Request quantité > stock

        OrderItemRequest itemRequest = new OrderItemRequest();

        itemRequest.setProductId(2L);
        itemRequest.setQuantity(5);



        CreateOrderRequest request = new CreateOrderRequest();

        request.setItems(
                List.of(itemRequest)
        );



        // Act + Assert

        InsufficientStockException exception =
                assertThrows(
                        InsufficientStockException.class,
                        () -> orderService.create(request)
                );


        assertEquals(
                "Not enough stock for product: Laptop HP",
                exception.getMessage()
        );



        // Vérifie que la commande n'est jamais sauvegardée

        verify(orderRepository, never())
                .save(any());

    }

}
