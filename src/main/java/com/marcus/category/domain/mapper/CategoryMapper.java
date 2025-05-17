package com.marcus.category.domain.mapper;

import com.marcus.category.domain.model.Category;
import com.marcus.category.infrastructure.jpa.entity.CategoryEntity;

import java.util.List;

public class CategoryMapper {

    private CategoryMapper() {

    }

    public static List<Category> toDomain(List<CategoryEntity> categories) {

        return categories.stream().map(CategoryMapper::toDomain).toList();
    }

    public static Category toDomain(CategoryEntity category) {

        return new Category(category.getId(), category.getName(), category.getDescription(), category.isEnabled());
    }
}
