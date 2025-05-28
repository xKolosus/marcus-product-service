package com.marcus.purchase.application.service;

import com.marcus.auth.application.auxiliar.JwtService;
import com.marcus.pagination.domain.model.PageRequest;
import com.marcus.pagination.domain.model.Pageable;
import com.marcus.pagination.mapper.PaginationMapper;
import com.marcus.product.infrastructure.jpa.entity.ProductEntity;
import com.marcus.product.infrastructure.jpa.repository.ProductJpaRepository;
import com.marcus.purchase.domain.mapper.PurchaseMapper;
import com.marcus.purchase.domain.model.Purchase;
import com.marcus.purchase.domain.service.PurchaseService;
import com.marcus.purchase.exception.PurchaseFailedException;
import com.marcus.purchase.infrastructure.controller.in.PurchaseRequest;
import com.marcus.purchase.infrastructure.jpa.criteria.PurchaseCriteria;
import com.marcus.purchase.infrastructure.jpa.mapper.PurchasePaginationMapper;
import com.marcus.purchase.infrastructure.jpa.repository.PurchaseJpaRepository;
import com.marcus.purchase.infrastructure.jpa.spec.PurchaseSpecification;
import com.marcus.subcategory.infrastructure.jpa.entity.SubCategoryEntity;
import com.marcus.user.infrastructure.jpa.entity.UserEntity;
import java.util.List;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultPurchaseService implements PurchaseService {

  private final PurchaseJpaRepository purchaseJpaRepository;
  private final PurchasePaginationMapper purchasePaginatedMapper;
  private final ProductJpaRepository productJpaRepository;

  private void productValidations(List<ProductEntity> products) {
    if (products.stream()
            .map(ProductEntity::getSubCategory)
            .map(SubCategoryEntity::getId)
            .distinct()
            .count()
        > 1) {
      throw new PurchaseFailedException("More than one subcategory was found, not allowed!");
    }

    if (products.stream().anyMatch(p -> p.getStock() == 0)) {
      throw new PurchaseFailedException("There is a product out of stock!");
    }

    if (products.stream()
        .map(ProductEntity::getSubCategory)
        .map(SubCategoryEntity::getCategory)
        .anyMatch(c -> !c.isEnabled())) {
      throw new PurchaseFailedException("The category is not enabled!");
    }
  }

  @Override
  public Pageable<Purchase> search(PageRequest request) {

    UserEntity user = JwtService.getLoggedUserId();

    CurrencyConversion conversion = MonetaryConversions.getConversion(user.getCurrency());

    return purchasePaginatedMapper.toDomain(
        purchaseJpaRepository.findAll(
            new PurchaseSpecification(new PurchaseCriteria(user.getId())),
            PaginationMapper.toPageRequest(request)),
        p -> PurchaseMapper.toDomain(p, conversion));
  }

  @Override
  public Purchase createPurchase(PurchaseRequest request) {

    UserEntity user = JwtService.getLoggedUserId();

    List<ProductEntity> products = productJpaRepository.findAllById(request.productIds());

    productValidations(products);

    CurrencyConversion conversion = MonetaryConversions.getConversion(user.getCurrency());

    return PurchaseMapper.toDomain(
        purchaseJpaRepository.save(PurchaseMapper.toEntity(products, user)), conversion);
  }
}
