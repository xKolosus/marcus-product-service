package com.marcus.category.domain.model;

import com.marcus.subcategory.domain.model.SubCategory;
import java.util.List;
import java.util.UUID;

public record Category(
    UUID id, String name, String description, boolean enabled, List<SubCategory> subCategories) {}
