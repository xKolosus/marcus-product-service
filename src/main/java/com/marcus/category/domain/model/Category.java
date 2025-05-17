package com.marcus.category.domain.model;

import java.util.UUID;

public record Category(UUID id, String name, String description, boolean enabled) {
}
