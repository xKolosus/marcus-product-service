package com.marcus.category.domain.service;

import com.marcus.category.domain.model.Category;
import com.marcus.category.infrastructure.controller.in.CategoryRequest;
import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<Category> findAll();

  URI createCategory(@Valid CategoryRequest request) throws URISyntaxException;

    void disableCategory(UUID categoryId);
}
