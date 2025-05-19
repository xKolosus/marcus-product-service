package com.marcus.subcategory.domain.mapper;

import com.marcus.subcategory.domain.model.SubCategory;
import com.marcus.subcategory.infrastructure.jpa.entity.SubCategoryEntity;
import java.util.List;

public class SubCategoryMapper {

  private SubCategoryMapper() {}

  public static SubCategory toDomain(SubCategoryEntity entity) {
    return new SubCategory(
        entity.getId(), entity.getName(), entity.getDescription(), entity.getCategory().getId());
  }

  public static List<SubCategory> toDomain(List<SubCategoryEntity> entities) {

    return entities.stream().map(SubCategoryMapper::toDomain).toList();
  }
}
