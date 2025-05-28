package com.marcus.purchase.infrastructure.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.marcus.pagination.domain.model.PageCount;
import com.marcus.pagination.domain.model.PageRequest;
import com.marcus.pagination.domain.model.Pageable;
import com.marcus.purchase.domain.model.Purchase;
import com.marcus.purchase.domain.service.PurchaseService;
import com.marcus.purchase.infrastructure.controller.in.PurchaseRequest;
import java.util.List;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class PurchaseControllerTest {

  @InjectMocks private PurchaseController controller;

  @Mock private PurchaseService purchaseService;

  @Test
  void getPurchases() {

    List<Purchase> purchases = Instancio.createList(Purchase.class);
    Pageable<Purchase> domain =
        new Pageable<>(
            purchases, new PageCount(purchases.size(), purchases.size(), purchases.size(), 0, 1));

    when(purchaseService.search(any())).thenReturn(domain);

    ResponseEntity<Pageable<Purchase>> response =
        controller.getPurchases(Instancio.create(PageRequest.class));

    assertNotNull(response.getBody());
    assertEquals(purchases.size(), response.getBody().getPage().totalContent());
  }

  @Test
  void createPurchase() {

    when(purchaseService.createPurchase(any())).thenReturn(Instancio.create(Purchase.class));

    ResponseEntity<Purchase> response =
        controller.createPurchase(Instancio.create(PurchaseRequest.class));

    assertNotNull(response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }
}
