package com.marcus.product.application;

import com.marcus.pagination.domain.model.Page;
import com.marcus.pagination.domain.model.PaginationMapper;
import com.marcus.product.domain.mapper.ProductMapper;
import com.marcus.product.domain.model.Product;
import com.marcus.product.domain.service.ProductService;
import com.marcus.product.infrastructure.controller.in.ProductRequest;
import com.marcus.product.infrastructure.controller.in.ProductSearchRequest;
import com.marcus.product.infrastructure.jpa.criteria.ProductCriteria;
import com.marcus.product.infrastructure.jpa.mapper.ProductPaginationMapper;
import com.marcus.product.infrastructure.jpa.repository.ProductJpaRepository;
import com.marcus.product.infrastructure.jpa.spec.ProductSpecification;
import com.marcus.subcategory.infrastructure.jpa.entity.SubCategoryEntity;
import com.marcus.subcategory.infrastructure.jpa.repository.SubCategoryJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultProductService implements ProductService {

  private final ProductJpaRepository productJpaRepository;
  private final ProductPaginationMapper productPaginationMapper;
  private final SubCategoryJpaRepository subCategoryJpaRepository;

  @Override
  public List<Product> findAll() {
    return ProductMapper.toDomain(productJpaRepository.findAll());
  }

  @Override
  public Page<Product> search(ProductSearchRequest request) {

    return productPaginationMapper.toDomain(
        productJpaRepository.findAll(
            new ProductSpecification(
                new ProductCriteria(
                    request.getCategoryId(), request.getSubCategoryId(), request.getSearchBy())),
            PaginationMapper.toPageRequest(request)),
        ProductMapper::toDomain);
  }

  @Override
  public Product createProduct(ProductRequest request) {
    Optional<SubCategoryEntity> subCategory =
        subCategoryJpaRepository.findById(request.subCategoryId());

    if (subCategory.isEmpty()) {
      throw new EntityNotFoundException("Subcategory does not exist!");
    }

    return ProductMapper.toDomain(
        productJpaRepository.save(ProductMapper.toEntity(request, subCategory.get())));
  }
}
