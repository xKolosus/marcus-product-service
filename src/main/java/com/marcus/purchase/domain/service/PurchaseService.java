package com.marcus.purchase.domain.service;

import com.marcus.pagination.domain.model.PageRequest;
import com.marcus.pagination.domain.model.Pageable;
import com.marcus.purchase.domain.model.Purchase;
import com.marcus.purchase.infrastructure.controller.in.PurchaseRequest;

public interface PurchaseService {
  Pageable<Purchase> search(PageRequest pageRequest);

  Purchase createPurchase(PurchaseRequest request);
}
