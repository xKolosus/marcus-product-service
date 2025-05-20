package com.marcus.auth.infrastructure.controller.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record AuthenticationRequest(@NotNull @Email String email, @NotNull String password) {}
