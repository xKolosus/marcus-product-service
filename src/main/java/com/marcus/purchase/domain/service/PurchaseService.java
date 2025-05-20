package com.marcus.purchase.domain.service;

import com.marcus.pagination.domain.model.Page;
import com.marcus.pagination.domain.model.PageRequest;
import com.marcus.purchase.domain.model.Purchase;
import com.marcus.purchase.infrastructure.controller.in.PurchaseRequest;

public interface PurchaseService {
  Page<Purchase> search(PageRequest pageRequest);

  Purchase createPurchase(PurchaseRequest request);
}
