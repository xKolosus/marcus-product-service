package com.marcus.category.application;

import com.marcus.category.domain.mapper.CategoryMapper;
import com.marcus.category.domain.model.Category;
import com.marcus.category.domain.service.CategoryService;
import com.marcus.category.infrastructure.controller.in.CategoryRequest;
import com.marcus.category.infrastructure.jpa.repository.CategoryJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultCategoryService implements CategoryService {

    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public List<Category> findAll() {

        return CategoryMapper.toDomain(categoryJpaRepository.findAll());
    }

  @Override
  public URI createCategory(CategoryRequest request) throws URISyntaxException {
    return new URI(
        "http://localhost:9999/api/category/"
            + categoryJpaRepository.save(CategoryMapper.toEntity(request)).getId().toString());
  }

  @Override
  public void disableCategory(UUID categoryId) {

    if (!categoryJpaRepository.existsById(categoryId)) {
      throw new EntityNotFoundException("Category was not found!");
    }

    categoryJpaRepository.disableCategory(categoryId);
  }
}
