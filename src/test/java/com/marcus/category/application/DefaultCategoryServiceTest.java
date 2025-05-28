package com.marcus.category.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.marcus.category.domain.model.Category;
import com.marcus.category.infrastructure.controller.in.CategoryRequest;
import com.marcus.category.infrastructure.jpa.entity.CategoryEntity;
import com.marcus.category.infrastructure.jpa.repository.CategoryJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DefaultCategoryServiceTest {

  @InjectMocks private DefaultCategoryService service;

  @Mock private CategoryJpaRepository categoryJpaRepository;

  @Test
  void findAll() {

    CategoryEntity c1 =
        new CategoryEntity(UUID.randomUUID(), "Bikes", "BIKES", true, new ArrayList<>());
    CategoryEntity c2 =
        new CategoryEntity(UUID.randomUUID(), "Skis", "Skis", false, new ArrayList<>());

    when(categoryJpaRepository.findAll()).thenReturn(List.of(c1, c2));

    List<Category> categories = service.findAll();

    assertEquals(2, categories.size());
    assertEquals(c1.getId(), categories.getFirst().id());
  }

  @Test
  void createCategory() throws URISyntaxException {

    CategoryRequest request = new CategoryRequest("A", "B", false);

    CategoryEntity entity =
        new CategoryEntity(
            UUID.randomUUID(),
            request.name(),
            request.description(),
            request.enabled(),
            new ArrayList<>());
    when(categoryJpaRepository.save(any())).thenReturn(entity);

    URI created = service.createCategory(request);

    assertTrue(created.getPath().endsWith(entity.getId().toString()));
  }

  @Test
  void disableCategory_doesNotExist_thenThrowEntityNotFound() {

    UUID categoryId = UUID.randomUUID();

    when(categoryJpaRepository.existsById(any())).thenReturn(false);

    EntityNotFoundException e =
        assertThrows(EntityNotFoundException.class, () -> service.disableCategory(categoryId));

    assertEquals("Category was not found!", e.getMessage());
  }
}
