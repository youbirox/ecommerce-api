package com.shop.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.backend.dto.request.CreateProductRequest;
import com.shop.backend.dto.response.ProductResponse;
import com.shop.backend.service.ProductService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//
//import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ProductController.class)
class ProductControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private ProductService productService;


    private final ObjectMapper objectMapper = new ObjectMapper();



    @Test
    void shouldCreateProduct() throws Exception {


        // Arrange

        CreateProductRequest request = new CreateProductRequest();

        request.setName("Laptop HP");
        request.setDescription("Gaming laptop");
        request.setPrice(699.99);
        request.setStock(10);



        ProductResponse response = new ProductResponse();

        response.setId(1L);
        response.setName("Laptop HP");
        response.setPrice(699.99);
        response.setStock(10);



        when(productService.create(any()))
                .thenReturn(response);



        // Act + Assert

        mockMvc.perform(
                        post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name")
                        .value("Laptop HP"))
                .andExpect(jsonPath("$.price")
                        .value(699.99));



        verify(productService)
                .create(any());

    }

}
