package com.marcus.user.domain.model;

import java.util.UUID;

public record User(UUID id, String email, String name, String surname) {}
