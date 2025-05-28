package com.marcus.product.infrastructure.controller.in;

import com.marcus.pagination.domain.model.PageRequest;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchRequest extends PageRequest {

  @NotNull(message = "Category cant be null!")
  private UUID categoryId;

  private UUID subCategoryId;
  private String searchBy;
}
