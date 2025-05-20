package com.marcus.product.infrastructure.controller.in;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ProductUpdateRequest(
    @NotNull(message = "Product id can not be null") UUID productId,
    @Min(value = 0, message = "Stock can not be negative") long newStock) {}
