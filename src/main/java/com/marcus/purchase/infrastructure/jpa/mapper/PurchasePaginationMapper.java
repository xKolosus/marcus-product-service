package com.marcus.purchase.infrastructure.jpa.mapper;

import com.marcus.pagination.domain.model.Page;
import com.marcus.pagination.mapper.PaginationMapper;
import com.marcus.purchase.domain.model.Purchase;
import com.marcus.purchase.infrastructure.jpa.entity.PurchaseEntity;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class PurchasePaginationMapper implements PaginationMapper<Purchase, PurchaseEntity> {
  @Override
  public Page<Purchase> toDomain(
      org.springframework.data.domain.Page<PurchaseEntity> page,
      Function<PurchaseEntity, Purchase> contentMapping) {
      return new Page<>(
              page.getContent().stream().map(contentMapping).toList(), PaginationMapper.toDomain(page));
  }
}
