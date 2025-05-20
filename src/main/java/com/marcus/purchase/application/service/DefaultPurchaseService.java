package com.marcus.purchase.application.service;

import com.marcus.auth.application.auxiliar.JwtService;
import com.marcus.pagination.domain.model.Page;
import com.marcus.pagination.domain.model.PageRequest;
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
import com.marcus.user.infrastructure.jpa.entity.UserEntity;
import com.marcus.user.infrastructure.jpa.repository.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultPurchaseService implements PurchaseService {

  private final PurchaseJpaRepository purchaseJpaRepository;
  private final PurchasePaginationMapper purchasePaginatedMapper;
  private final UserJpaRepository userJpaRepository;
  private final ProductJpaRepository productJpaRepository;

  @Override
  public Page<Purchase> search(PageRequest request) {

    Optional<UserEntity> user = userJpaRepository.findById(JwtService.getLoggedUserId());

    if (user.isEmpty()) {
      throw new EntityNotFoundException("User not found!");
    }

    CurrencyConversion conversion = MonetaryConversions.getConversion(user.get().getCurrency());

    return purchasePaginatedMapper.toDomain(
        purchaseJpaRepository.findAll(
            new PurchaseSpecification(new PurchaseCriteria(user.get().getId())),
            PaginationMapper.toPageRequest(request)),
        p -> PurchaseMapper.toDomain(p, conversion));
  }

  @Override
  public Purchase createPurchase(PurchaseRequest request) {

    Optional<UserEntity> user = userJpaRepository.findById(JwtService.getLoggedUserId());

    if (user.isEmpty()) {
      throw new EntityNotFoundException("User not found!");
    }

    List<ProductEntity> products = productJpaRepository.findAllById(request.productIds());

    if (products.stream().map(ProductEntity::getSubCategory).distinct().count() > 1) {
      throw new PurchaseFailedException("More than one subcategory was found, not allowed!");
    }

    if (products.stream().anyMatch(p -> p.getStock() == 0)) {
      throw new PurchaseFailedException("There is a product out of stock!");
    }

    CurrencyConversion conversion = MonetaryConversions.getConversion(user.get().getCurrency());

    return PurchaseMapper.toDomain(
        purchaseJpaRepository.save(
            PurchaseMapper.toEntity(
                products, userJpaRepository.getReferenceById(user.get().getId()))),
        conversion);
  }
}
