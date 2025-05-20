package com.marcus.auth.infrastructure.controller.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
    @NotNull(message = "Name cannot be null") String name,
    @NotNull(message = "Surname cannot be null") String surname,
    @NotNull(message = "Email cannot be null") @Email String email,
    @NotNull(message = "Password cannot be null") String password) {}
