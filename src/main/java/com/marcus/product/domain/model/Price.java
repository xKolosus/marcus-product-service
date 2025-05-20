package com.marcus.product.domain.model;

import org.hibernate.validator.constraints.Currency;

public record Price(double amount, @Currency(value = {"EUR", "GBP", "USD"}) String currency) {}
