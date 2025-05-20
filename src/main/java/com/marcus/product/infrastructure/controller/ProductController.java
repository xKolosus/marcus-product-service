package com.marcus.product.infrastructure.controller;

import com.marcus.pagination.domain.model.Page;
import com.marcus.product.domain.model.Product;
import com.marcus.product.domain.service.ProductService;
import com.marcus.product.infrastructure.controller.in.ProductRequest;
import com.marcus.product.infrastructure.controller.in.ProductSearchRequest;
import com.marcus.product.infrastructure.controller.in.ProductUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Tag(name = "Product endpoints")
public class ProductController {

  private final ProductService productService;

  @GetMapping
  @Operation(
      description = "This retrieves all the actual products",
      summary = "All users are allowed to receive this data.")
  public ResponseEntity<List<Product>> findAll() {

    return ResponseEntity.ok(productService.findAll());
  }

  @PostMapping("/search")
  @Operation(
      summary = "This retrieves the products filtered",
      description =
          """
    All users are allowed to receive this data.
    Important, the possible filters are:

          · By category.
          · By subcategory.
          · By text field (name and description match)
    """)
  public ResponseEntity<Page<Product>> search(@RequestBody ProductSearchRequest request) {

    return ResponseEntity.ok(productService.search(request));
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(
      description = "This create one product",
      summary = "Only admins are allowed to create products!")
  public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest request) {

    return ResponseEntity.ok(productService.createProduct(request));
  }

  @PatchMapping
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(
      description = "This updates one product stock",
      summary = "Only admins are allowed to update products stock!")
  public ResponseEntity<Product> updateStock(@RequestBody @Valid ProductUpdateRequest request) {

    productService.updateStock(request);

    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{productId}")
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(
      description = "This updates one product",
      summary = "Only admins are allowed to update products!")
  public ResponseEntity<Product> updateProduct(
      @RequestBody @Valid ProductRequest request, @PathVariable UUID productId) {

    return ResponseEntity.ok(productService.updateProduct(request, productId));
  }
}
