package com.marcus.auth.domain.service;

import com.marcus.auth.infrastructure.controller.in.AuthenticationRequest;
import com.marcus.auth.infrastructure.controller.in.RegisterRequest;
import com.marcus.auth.infrastructure.controller.out.AuthenticationResponse;
import com.marcus.user.domain.model.User;

public interface AuthenticationService {
  User register(RegisterRequest request);

  AuthenticationResponse authenticate(AuthenticationRequest request);
}
