package com.marcus.product.infrastructure.controller.in;

import com.marcus.pagination.domain.model.PageRequest;
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

  private UUID categoryId;
  private UUID subCategoryId;
  private String searchBy;
}
