package com.marcus.category.infrastructure.controller;

import com.marcus.category.domain.model.Category;
import com.marcus.category.domain.service.CategoryService;
import com.marcus.category.infrastructure.controller.in.CategoryRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@Tag(name = "Category endpoints")
public class CategoryController {

    private final CategoryService categoryService;

  @GetMapping
  @Operation(
      description = "This retrieves all the actual categories",
      summary = "All users are allowed to receive this data.")
  public ResponseEntity<List<Category>> findAll() {

        return ResponseEntity.ok(categoryService.findAll());
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(
      description = "This create a new category",
      summary = "Only admins are allowed to create new categories!")
  public ResponseEntity<URI> createCategory(@RequestBody @Valid CategoryRequest request)
      throws URISyntaxException {

    return ResponseEntity.created(categoryService.createCategory(request)).build();
  }

  @PatchMapping("/disable/{categoryId}")
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(
      description = "Disable a category",
      summary = "Only admins are allowed to disable categories!")
  public ResponseEntity<Void> disableCategory(@PathVariable UUID categoryId) {

    categoryService.disableCategory(categoryId);

    return ResponseEntity.noContent().build();
  }
}
