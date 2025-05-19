package com.marcus.product.infrastructure.jpa.spec;

import static com.marcus.pagination.domain.constant.PaginationConstant.LIKE_PATTERN;

import com.marcus.category.infrastructure.jpa.entity.CategoryEntity;
import com.marcus.product.infrastructure.jpa.criteria.ProductCriteria;
import com.marcus.product.infrastructure.jpa.entity.ProductEntity;
import com.marcus.subcategory.infrastructure.jpa.entity.SubCategoryEntity;
import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class ProductSpecification implements Specification<ProductEntity> {

  private transient ProductCriteria criteria;

  @Override
  public Predicate toPredicate(
      Root<ProductEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    List<Predicate> predicates = new ArrayList<>();
    Join<SubCategoryEntity, ProductEntity> joinSubCategory = root.join("subCategory");
    if (criteria.categoryId() != null) {
      Join<CategoryEntity, SubCategoryEntity> joinCategory = joinSubCategory.join("category");

      predicates.add(cb.equal(joinCategory.get("id"), criteria.categoryId()));
    }

    if (criteria.subCategoryId() != null) {

      predicates.add(cb.equal(joinSubCategory.get("id"), criteria.subCategoryId()));
    }

    if (criteria.searchBy() != null) {

      predicates.add(
          cb.or(
              cb.like(
                  cb.lower(root.get("name")),
                  LIKE_PATTERN + criteria.searchBy().toLowerCase() + LIKE_PATTERN),
              cb.like(
                  cb.lower(root.get("description")),
                  LIKE_PATTERN + criteria.searchBy().toLowerCase() + LIKE_PATTERN)));
    }

    return cb.and(predicates.toArray(Predicate[]::new));
  }
}
