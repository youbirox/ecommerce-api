package com.shop.backend.service;

import com.shop.backend.dto.request.CreateProductRequest;
import com.shop.backend.dto.response.ProductResponse;
import com.shop.backend.dto.request.UpdateProductRequest;
import com.shop.backend.entity.Product;
import com.shop.backend.exception.ProductNotFoundException;
import com.shop.backend.mapper.ProductMapper;
import com.shop.backend.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository,
                          ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

//    public Product create(CreateProductRequest request) {
//
//        Product product = productMapper.toEntity(request);
//
//        product.setCreatedAt(LocalDateTime.now());
//        return productRepository.save(product);
//    }

    public ProductResponse create(CreateProductRequest request) {

        Product product = productMapper.toEntity(request);

        product.setCreatedAt(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        return productMapper.toResponse(savedProduct);
    }

    public List<ProductResponse> findAll() {

        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    public ProductResponse findById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        return productMapper.toResponse(product);
    }

    public ProductResponse update(Long id, UpdateProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        Product updatedProduct = productRepository.save(product);

        return productMapper.toResponse(updatedProduct);
    }

    public void delete(Long id) {

        productRepository.deleteById(id);

    }

    public Page<ProductResponse> findAll(Pageable pageable) {

        return productRepository.findAll(pageable)
                .map(productMapper::toResponse);
    }

    public List<ProductResponse> searchByName(String name) {

        return productRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }
}
