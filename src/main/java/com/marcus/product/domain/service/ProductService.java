package com.marcus.product.domain.service;

import com.marcus.pagination.domain.model.Page;
import com.marcus.product.domain.model.Product;
import com.marcus.product.infrastructure.controller.in.ProductRequest;
import com.marcus.product.infrastructure.controller.in.ProductSearchRequest;
import jakarta.validation.Valid;

import java.util.List;

public interface ProductService {
    List<Product> findAll();

    Page<Product> search(ProductSearchRequest request);

    Product createProduct(@Valid ProductRequest request);
}
