package com.marcus.product.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.marcus.product.domain.model.Price;
import com.marcus.product.domain.model.Product;
import com.marcus.product.infrastructure.controller.in.ProductRequest;
import com.marcus.product.infrastructure.controller.in.ProductSearchRequest;
import com.marcus.product.infrastructure.controller.in.ProductUpdateRequest;
import com.marcus.product.infrastructure.jpa.entity.ProductEntity;
import com.marcus.product.infrastructure.jpa.mapper.ProductPaginationMapper;
import com.marcus.product.infrastructure.jpa.repository.ProductJpaRepository;
import com.marcus.product.infrastructure.jpa.spec.ProductSpecification;
import com.marcus.subcategory.infrastructure.jpa.entity.SubCategoryEntity;
import com.marcus.subcategory.infrastructure.jpa.repository.SubCategoryJpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.instancio.Instancio;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class DefaultProductServiceTest {

  @InjectMocks private DefaultProductService service;

  @Mock private ProductJpaRepository productJpaRepository;

  @Mock(answer = Answers.CALLS_REAL_METHODS)
  private ProductPaginationMapper productPaginationMapper;

  @Mock private SubCategoryJpaRepository subCategoryJpaRepository;

  @Test
  void findAll() {

    List<ProductEntity> products = Instancio.createList(ProductEntity.class);

    products.forEach(p -> p.setPrice(Money.of(10.0, "EUR")));

    when(productJpaRepository.findAll()).thenReturn(products);

    List<Product> domain = service.findAll();

    assertNotNull(domain);
    assertEquals(products.getFirst().getId(), domain.getFirst().id());
    assertEquals(
        products.getFirst().getSubCategory().getId(), domain.getFirst().subCategory().id());
  }

  @Test
  void search() {

    ProductSearchRequest request = Instancio.create(ProductSearchRequest.class);

    List<ProductEntity> products = Instancio.createList(ProductEntity.class);
    products.forEach(p -> p.setPrice(Money.of(10.0, "EUR")));

    Page<ProductEntity> page =
        new PageImpl<>(products, PageRequest.of(0, products.size()), products.size());

    when(productJpaRepository.findAll(any(ProductSpecification.class), any(Pageable.class)))
        .thenReturn(page);

    com.marcus.pagination.domain.model.Pageable<Product> result = service.search(request);

    assertNotNull(result);
    assertEquals(page.getTotalElements(), result.getPage().totalContent());
  }

  @Test
  void createProduct() {

    ProductRequest request = getRequest();
    when(subCategoryJpaRepository.findById(any()))
        .thenReturn(Optional.of(Instancio.create(SubCategoryEntity.class)));
    ProductEntity entity = getProductEntity();
    when(productJpaRepository.save(any())).thenReturn(entity);

    Product result = service.createProduct(request);

    assertNotNull(result);
    assertEquals(entity.getName(), result.name());
  }

  private ProductEntity getProductEntity() {
    ProductEntity entity = Instancio.create(ProductEntity.class);
    entity.setPrice(Money.of(25, "EUR"));
    return entity;
  }

  private ProductRequest getRequest() {
    return new ProductRequest("a", "b", UUID.randomUUID(), null, new Price(14.0, "USD"));
  }

  @Test
  void updateProduct() {

    ProductRequest request = getRequest();
    ProductEntity entity = getProductEntity();

    when(productJpaRepository.findById(any())).thenReturn(Optional.of(entity));
    when(productJpaRepository.save(any())).thenReturn(entity);
    when(subCategoryJpaRepository.findById(any()))
        .thenReturn(Optional.of(Instancio.create(SubCategoryEntity.class)));

    Product updated = service.updateProduct(request, UUID.randomUUID());

    assertNotNull(updated);
    assertEquals(entity.getDescription(), updated.description());
  }

  @Test
  void updateStock() {

    ProductUpdateRequest request = new ProductUpdateRequest(UUID.randomUUID(), 2);
    ProductEntity entity = getProductEntity();

    when(productJpaRepository.findById(any())).thenReturn(Optional.of(entity));

    service.updateStock(request);

    verify(productJpaRepository, atLeastOnce()).findById(request.productId());
    verify(productJpaRepository, atLeastOnce()).save(entity);
  }

  @Test
  void findById() {

    UUID productId = UUID.randomUUID();
    ProductEntity entity = getProductEntity();

    when(productJpaRepository.findById(any())).thenReturn(Optional.of(entity));

    Product result = service.findById(productId);

    assertNotNull(result);
    assertEquals(entity.getStock(), result.stock());
  }
}
