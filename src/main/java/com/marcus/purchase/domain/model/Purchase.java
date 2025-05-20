package com.marcus.purchase.domain.model;

import com.marcus.product.domain.model.Price;
import com.marcus.product.domain.model.Product;
import java.util.List;
import java.util.UUID;

public record Purchase(UUID id, UUID userId, List<Product> products, Price totalAmount) {}
