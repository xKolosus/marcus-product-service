package com.marcus.product.infrastructure.jpa.mapper;

import com.marcus.pagination.domain.model.Pageable;
import com.marcus.pagination.mapper.PaginationMapper;
import com.marcus.product.domain.model.Product;
import com.marcus.product.infrastructure.jpa.entity.ProductEntity;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class ProductPaginationMapper implements PaginationMapper<Product, ProductEntity> {

  @Override
  public Pageable<Product> toDomain(
      org.springframework.data.domain.Page<ProductEntity> page,
      Function<ProductEntity, Product> contentMapping) {
    return new Pageable<>(
        page.getContent().stream().map(contentMapping).toList(), PaginationMapper.toDomain(page));
  }
}
