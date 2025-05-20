package com.marcus.product.infrastructure.controller.in;

import com.marcus.product.domain.model.Price;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ProductRequest(
    @NotNull String name,
    @NotNull String description,
    @NotNull UUID subCategoryId,
    String photoUrl,
    @NotNull Price monetaryAmount) {}
