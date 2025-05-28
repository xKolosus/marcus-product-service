package com.marcus.category.infrastructure.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.marcus.category.domain.model.Category;
import com.marcus.category.domain.service.CategoryService;
import com.marcus.category.infrastructure.controller.in.CategoryRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

  @InjectMocks private CategoryController controller;

  @Mock private CategoryService categoryService;

  @Test
  void findAll() {

    List<Category> categories =
        List.of(new Category(UUID.randomUUID(), "a", "b", true, new ArrayList<>()));
    when(categoryService.findAll()).thenReturn(categories);

    ResponseEntity<List<Category>> response = controller.findAll();

    assertNotNull(response.getBody());
    assertEquals(categories.getFirst().id(), response.getBody().getFirst().id());
  }

  @Test
  void createCategory() throws URISyntaxException {

    CategoryRequest request = new CategoryRequest("a", "b", false);

    UUID categoryId = UUID.randomUUID();
    URI expectedURI = new URI("http://localhost:9999/api/category/" + categoryId);
    when(categoryService.createCategory(any())).thenReturn(expectedURI);

    ResponseEntity<URI> response = controller.createCategory(request);

    assertNotNull(response.getHeaders());
    assertNotNull(response.getHeaders().get(HttpHeaders.LOCATION));
    assertEquals(
        expectedURI.toString(),
        Objects.requireNonNull(response.getHeaders().get(HttpHeaders.LOCATION)).getFirst());
  }

  @Test
  void disableCategory() {

    UUID categoryId = UUID.randomUUID();

    ResponseEntity<Void> response = controller.disableCategory(categoryId);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    verify(categoryService, atLeastOnce()).disableCategory(categoryId);
  }
}
