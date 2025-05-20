package com.marcus.product.infrastructure.controller.in;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import javax.money.MonetaryAmount;

public record ProductRequest(
    @NotNull String name,
    @NotNull String description,
    @NotNull UUID subCategoryId,
    String photoUrl,
    @NotNull MonetaryAmount monetaryAmount) {}
