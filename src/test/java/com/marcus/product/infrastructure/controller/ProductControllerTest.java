package com.marcus.product.infrastructure.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.marcus.pagination.domain.model.PageCount;
import com.marcus.pagination.domain.model.Pageable;
import com.marcus.product.domain.model.Product;
import com.marcus.product.domain.service.ProductService;
import com.marcus.product.infrastructure.controller.in.ProductRequest;
import com.marcus.product.infrastructure.controller.in.ProductSearchRequest;
import com.marcus.product.infrastructure.controller.in.ProductUpdateRequest;
import java.util.List;
import java.util.UUID;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

  @InjectMocks private ProductController controller;

  @Mock private ProductService productService;

  @Test
  void findAll() {

    when(productService.findAll()).thenReturn(Instancio.createList(Product.class));

    ResponseEntity<List<Product>> response = controller.findAll();

    assertNotNull(response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void findById() {

    when(productService.findById(any())).thenReturn(Instancio.create(Product.class));

    ResponseEntity<Product> response = controller.findById(UUID.randomUUID());

    assertNotNull(response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void search() {

    List<Product> products = Instancio.createList(Product.class);
    Pageable<Product> domain =
        new Pageable<>(products, new PageCount(15, products.size(), products.size(), 0, 1));

    when(productService.search(any())).thenReturn(domain);

    ResponseEntity<Pageable<Product>> response =
        controller.search(Instancio.create(ProductSearchRequest.class));

    assertNotNull(response.getBody());
    assertEquals(domain.getContent().size(), response.getBody().getContent().size());
  }

  @Test
  void createProduct() {

    when(productService.createProduct(any())).thenReturn(Instancio.create(Product.class));

    ResponseEntity<Product> response =
        controller.createProduct(Instancio.create(ProductRequest.class));

    assertNotNull(response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void updateStock() {

    ResponseEntity<Void> response =
        controller.updateStock(Instancio.create(ProductUpdateRequest.class));

    assertNull(response.getBody());
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
  }

  @Test
  void updateProduct() {

    when(productService.updateProduct(any(), any())).thenReturn(Instancio.create(Product.class));

    ResponseEntity<Product> response =
        controller.updateProduct(Instancio.create(ProductRequest.class), UUID.randomUUID());

    assertNotNull(response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }
}
