package com.marcus.subcategory.domain.model;

import java.util.UUID;

public record SubCategory(UUID id, String name, String description, UUID categoryId) {}
