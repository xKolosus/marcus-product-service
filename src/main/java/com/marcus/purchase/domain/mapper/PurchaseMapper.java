package com.marcus.purchase.domain.mapper;

import com.marcus.product.domain.mapper.ProductMapper;
import com.marcus.product.domain.model.Price;
import com.marcus.product.infrastructure.jpa.entity.ProductEntity;
import com.marcus.purchase.domain.model.Purchase;
import com.marcus.purchase.infrastructure.jpa.entity.PurchaseEntity;
import com.marcus.user.infrastructure.jpa.entity.UserEntity;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import javax.money.*;
import javax.money.convert.CurrencyConversion;
import org.javamoney.moneta.FastMoney;
import org.javamoney.moneta.function.MonetaryFunctions;

public class PurchaseMapper {

  private PurchaseMapper() {}

  public static Purchase toDomain(PurchaseEntity entity, CurrencyConversion conversion) {

    List<ProductEntity> products = entity.getProductsBought();
    MonetaryAmount userMoney =
        products.stream()
            .map(ProductEntity::getPrice)
            .map(p -> p.with(conversion))
            .reduce(MonetaryFunctions::sum)
            .orElse(FastMoney.of(0, conversion.getCurrency()));

    return new Purchase(
        entity.getId(),
        entity.getUser().getId(),
        ProductMapper.toDomain(products),
        new Price(
            userMoney
                .getNumber()
                .numberValue(BigDecimal.class)
                .setScale(2, RoundingMode.HALF_EVEN)
                .doubleValue(),
            conversion.getCurrency().getCurrencyCode()));
  }

  public static PurchaseEntity toEntity(List<ProductEntity> productEntities, UserEntity user) {

    PurchaseEntity entity = PurchaseEntity.builder().user(user).details(new ArrayList<>()).build();

    productEntities.forEach(
        product -> {
          product.setStock(product.getStock() - 1);
          entity.addDetail(product);
        });

    return entity;
  }
}
