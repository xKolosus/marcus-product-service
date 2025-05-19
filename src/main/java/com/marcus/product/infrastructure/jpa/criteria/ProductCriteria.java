package com.marcus.product.infrastructure.jpa.criteria;

import java.util.UUID;

public record ProductCriteria(UUID categoryId, UUID subCategoryId, String searchBy) {}
