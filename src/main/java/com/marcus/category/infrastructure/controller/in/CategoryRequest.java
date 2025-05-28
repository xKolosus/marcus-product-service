package com.marcus.category.infrastructure.controller.in;

import jakarta.validation.constraints.NotNull;

public record CategoryRequest(
    @NotNull(message = "Name can not be null") String name,
    @NotNull(message = "Description can not be null") String description,
    boolean enabled) {}
