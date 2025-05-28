package com.marcus.purchase.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.marcus.pagination.domain.model.PageRequest;
import com.marcus.product.infrastructure.jpa.entity.ProductEntity;
import com.marcus.product.infrastructure.jpa.repository.ProductJpaRepository;
import com.marcus.purchase.domain.model.Purchase;
import com.marcus.purchase.exception.PurchaseFailedException;
import com.marcus.purchase.infrastructure.controller.in.PurchaseRequest;
import com.marcus.purchase.infrastructure.jpa.entity.PurchaseEntity;
import com.marcus.purchase.infrastructure.jpa.mapper.PurchasePaginationMapper;
import com.marcus.purchase.infrastructure.jpa.repository.PurchaseJpaRepository;
import com.marcus.purchase.infrastructure.jpa.spec.PurchaseSpecification;
import com.marcus.user.infrastructure.jpa.entity.UserEntity;
import java.util.List;
import java.util.UUID;
import org.instancio.Instancio;
import org.javamoney.moneta.FastMoney;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
class DefaultPurchaseServiceTest {

  @InjectMocks private DefaultPurchaseService service;

  @Mock private PurchaseJpaRepository purchaseJpaRepository;

  @Mock(answer = Answers.CALLS_REAL_METHODS)
  private PurchasePaginationMapper purchasePaginationMapper;

  @Mock private ProductJpaRepository productJpaRepository;

  @BeforeEach
  void setUp() {

    UserEntity t = Instancio.create(UserEntity.class);
    t.setCurrency("EUR");
    Authentication authentication = mock(Authentication.class);
    SecurityContext context = mock(SecurityContext.class);
    when(authentication.getPrincipal()).thenReturn(t);
    when(context.getAuthentication()).thenReturn(authentication);

    SecurityContextHolder.setContext(context);
  }

  @AfterEach
  void tearDown() {
    SecurityContextHolder.clearContext();
  }

  @Test
  void createPurchase_thenMoreThanOneSubCategory_thenThrow() {
    PurchaseRequest request = Instancio.create(PurchaseRequest.class);
    List<ProductEntity> products = Instancio.createList(ProductEntity.class);

    when(productJpaRepository.findAllById(any())).thenReturn(products);
    products.getFirst().getSubCategory().setId(UUID.randomUUID());
    PurchaseFailedException e =
        assertThrows(PurchaseFailedException.class, () -> service.createPurchase(request));

    assertEquals("More than one subcategory was found, not allowed!", e.getMessage());
  }

  @Test
  void createPurchase_thenNoStock_thenThrow() {
    PurchaseRequest request = Instancio.create(PurchaseRequest.class);
    List<ProductEntity> products = Instancio.createList(ProductEntity.class);
    UUID subCategoryId = UUID.randomUUID();
    products.forEach(p -> p.getSubCategory().setId(subCategoryId));
    products.getFirst().setStock(0);

    when(productJpaRepository.findAllById(any())).thenReturn(products);
    PurchaseFailedException e =
        assertThrows(PurchaseFailedException.class, () -> service.createPurchase(request));

    assertEquals("There is a product out of stock!", e.getMessage());
  }

  @Test
  void createPurchase_thenCategoryNotEnabled_thenThrow() {
    PurchaseRequest request = Instancio.create(PurchaseRequest.class);
    List<ProductEntity> products = Instancio.createList(ProductEntity.class);
    UUID subCategoryId = UUID.randomUUID();
    products.forEach(
        p -> {
          p.getSubCategory().setId(subCategoryId);
          p.setStock(2);
        });

    when(productJpaRepository.findAllById(any())).thenReturn(products);
    products.getFirst().getSubCategory().getCategory().setEnabled(false);
    PurchaseFailedException e =
        assertThrows(PurchaseFailedException.class, () -> service.createPurchase(request));

    assertEquals("The category is not enabled!", e.getMessage());
  }

  @Test
  void createPurchase_thenWorks() {
    PurchaseRequest request = Instancio.create(PurchaseRequest.class);
    List<ProductEntity> products = Instancio.createList(ProductEntity.class);
    UUID subCategoryId = UUID.randomUUID();
    products.forEach(
        p -> {
          p.getSubCategory().setId(subCategoryId);
          p.setStock(2);
          p.getSubCategory().getCategory().setEnabled(true);
        });

    when(productJpaRepository.findAllById(any())).thenReturn(products);
    PurchaseEntity entity = Instancio.create(PurchaseEntity.class);
    entity.getDetails().forEach(d -> d.getProduct().setPrice(FastMoney.of(2.0, "EUR")));
    when(purchaseJpaRepository.save(any())).thenReturn(entity);

    Purchase purchase = service.createPurchase(request);

    assertNotNull(purchase);
  }

  @Test
  void search() {
    PageRequest request = Instancio.create(PageRequest.class);
    List<PurchaseEntity> purchases = Instancio.createList(PurchaseEntity.class);
    purchases.forEach(
        purch -> purch.getProductsBought().forEach(p -> p.setPrice(FastMoney.of(10.0, "EUR"))));
    Page<PurchaseEntity> page =
        new PageImpl<>(
            purchases,
            org.springframework.data.domain.PageRequest.of(0, purchases.size()),
            purchases.size());

    when(purchaseJpaRepository.findAll(any(PurchaseSpecification.class), any(Pageable.class)))
        .thenReturn(page);

    com.marcus.pagination.domain.model.Pageable<Purchase> result = service.search(request);

    assertNotNull(result);
    assertEquals(page.getTotalElements(), result.getPage().totalContent());
  }
}
