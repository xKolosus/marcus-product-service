package com.marcus.product.domain.model;

import com.marcus.subcategory.domain.model.SubCategory;
import java.util.UUID;

public record Product(
    UUID id,
    String name,
    String description,
    SubCategory subCategory,
    String photoUrl,
    String formattedPrice) {}
