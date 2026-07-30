package com.shop.backend.service;

import com.shop.backend.dto.request.CreateProductRequest;
import com.shop.backend.dto.request.UpdateProductRequest;
import com.shop.backend.dto.response.ProductResponse;
import com.shop.backend.entity.Product;
import com.shop.backend.exception.ProductNotFoundException;
import com.shop.backend.mapper.ProductMapper;
import com.shop.backend.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldCreateProduct() {

        CreateProductRequest request = new CreateProductRequest();


        Product product = new Product();

        product.setId(1L);
        product.setName("Laptop HP");


        ProductResponse response = new ProductResponse();

        response.setId(1L);
        response.setName("Laptop HP");



        when(productMapper.toEntity(request))
                .thenReturn(product);


        when(productRepository.save(any(Product.class)))
                .thenReturn(product);


        when(productMapper.toResponse(product))
                .thenReturn(response);



        ProductResponse result =
                productService.create(request);



        assertEquals(
                "Laptop HP",
                result.getName()
        );
    }


    @Test
    void shouldThrowExceptionWhenProductNotFound() {

        // Arrange
        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        // Act + Assert
        ProductNotFoundException exception = assertThrows(
                ProductNotFoundException.class,
                () -> productService.findById(1L)
        );

        assertEquals("Product not found", exception.getMessage());

        verify(productRepository).findById(1L);
    }

    @Test
    void shouldUpdateProduct() {

        // Arrange
        Long productId = 1L;

        UpdateProductRequest request = new UpdateProductRequest();
        request.setName("Laptop Dell");
        request.setDescription("Dell i7");
        request.setPrice(999.99);
        request.setStock(8);

        Product product = new Product();
        product.setId(productId);
        product.setName("Old");
        product.setDescription("Old");
        product.setPrice(500.0);
        product.setStock(2);

        ProductResponse response = new ProductResponse();
        response.setId(productId);
        response.setName("Laptop Dell");
        response.setDescription("Dell i7");
        response.setPrice(999.99);
        response.setStock(8);

        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));

        when(productRepository.save(any(Product.class)))
                .thenReturn(product);

        when(productMapper.toResponse(product))
                .thenReturn(response);

        // Act
        ProductResponse result = productService.update(productId, request);

        // Assert
        assertNotNull(result);
        assertEquals("Laptop Dell", result.getName());
        assertEquals(999.99, result.getPrice());
        assertEquals(8, result.getStock());

        verify(productRepository).findById(productId);
        verify(productRepository).save(product);
        verify(productMapper).toResponse(product);
    }

    @Test
    void shouldDeleteProduct() {

        // Arrange
        Long productId = 1L;

        // Act
        productService.delete(productId);

        // Assert
        verify(productRepository)
                .deleteById(productId);
    }

    @Test
    void shouldSearchProductsByName() {

        // Arrange
        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop HP");
        product.setPrice(699.99);

        ProductResponse response = new ProductResponse();
        response.setId(1L);
        response.setName("Laptop HP");
        response.setPrice(699.99);

        when(productRepository.findByNameContainingIgnoreCase("laptop"))
                .thenReturn(List.of(product));

        when(productMapper.toResponse(product))
                .thenReturn(response);


        // Act
        List<ProductResponse> result =
                productService.searchByName("laptop");


        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Laptop HP", result.get(0).getName());

        verify(productRepository)
                .findByNameContainingIgnoreCase("laptop");

        verify(productMapper)
                .toResponse(product);
    }

    @Test
    void shouldFindAllProducts() {

        // Arrange
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Laptop HP");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Mouse Logitech");


        ProductResponse response1 = new ProductResponse();
        response1.setId(1L);
        response1.setName("Laptop HP");

        ProductResponse response2 = new ProductResponse();
        response2.setId(2L);
        response2.setName("Mouse Logitech");


        when(productRepository.findAll())
                .thenReturn(List.of(product1, product2));

        when(productMapper.toResponse(product1))
                .thenReturn(response1);

        when(productMapper.toResponse(product2))
                .thenReturn(response2);


        // Act
        List<ProductResponse> result = productService.findAll();


        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals("Laptop HP", result.get(0).getName());
        assertEquals("Mouse Logitech", result.get(1).getName());


        verify(productRepository)
                .findAll();

        verify(productMapper)
                .toResponse(product1);

        verify(productMapper)
                .toResponse(product2);
    }



}
