package com.marcus.product.domain.service;

import com.marcus.pagination.domain.model.Pageable;
import com.marcus.product.domain.model.Product;
import com.marcus.product.infrastructure.controller.in.ProductRequest;
import com.marcus.product.infrastructure.controller.in.ProductSearchRequest;
import com.marcus.product.infrastructure.controller.in.ProductUpdateRequest;
import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<Product> findAll();

  Pageable<Product> search(ProductSearchRequest request);

  Product createProduct(ProductRequest request);

  Product updateProduct(ProductRequest request, UUID productId);

  void updateStock(ProductUpdateRequest productUpdateRequest);

  Product findById(UUID productId);
}
