package com.marcus.product.domain.mapper;

import com.marcus.product.domain.model.Product;
import com.marcus.product.infrastructure.controller.in.ProductRequest;
import com.marcus.product.infrastructure.jpa.entity.ProductEntity;
import com.marcus.subcategory.domain.mapper.SubCategoryMapper;
import com.marcus.subcategory.infrastructure.jpa.entity.SubCategoryEntity;
import java.util.List;
import javax.money.MonetaryAmount;
import org.javamoney.moneta.Money;

public class ProductMapper {

  private ProductMapper() {}

  public static List<Product> toDomain(List<ProductEntity> entities) {

    return entities.stream().map(ProductMapper::toDomain).toList();
  }

  public static Product toDomain(ProductEntity entity) {
    return new Product(
        entity.getId(),
        entity.getName(),
        entity.getDescription(),
        SubCategoryMapper.toDomain(entity.getSubCategory()),
        entity.getPhotoUrl(),
        getFormattedPrice(entity.getPrice()));
  }

  private static String getFormattedPrice(MonetaryAmount price) {

    return price.getNumber().doubleValueExact() + price.getCurrency().getCurrencyCode();
  }

  public static ProductEntity toEntity(ProductRequest request, SubCategoryEntity subCategory) {

    return ProductEntity.builder()
        .name(request.name())
        .description(request.description())
        .subCategory(subCategory)
        .price(
            Money.of(request.monetaryAmount().getNumber(), request.monetaryAmount().getCurrency()))
        .build();
  }
}
