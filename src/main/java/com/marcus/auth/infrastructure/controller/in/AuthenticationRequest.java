package com.marcus.auth.infrastructure.controller.in;

import jakarta.validation.constraints.NotNull;

public record AuthenticationRequest(@NotNull String email, @NotNull String password) {}
