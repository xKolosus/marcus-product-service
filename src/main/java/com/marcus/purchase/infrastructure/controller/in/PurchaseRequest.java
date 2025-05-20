package com.marcus.purchase.infrastructure.controller.in;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public record PurchaseRequest(
    @NotNull(message = "Product list can not be null!") List<UUID> productIds) {}
