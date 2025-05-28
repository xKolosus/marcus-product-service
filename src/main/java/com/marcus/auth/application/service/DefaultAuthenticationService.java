package com.marcus.auth.application.service;

import com.marcus.auth.application.auxiliar.JwtService;
import com.marcus.auth.application.exception.UserAlreadyRegisteredException;
import com.marcus.auth.domain.service.AuthenticationService;
import com.marcus.auth.infrastructure.controller.in.AuthenticationRequest;
import com.marcus.auth.infrastructure.controller.in.RegisterRequest;
import com.marcus.auth.infrastructure.controller.out.AuthenticationResponse;
import com.marcus.user.domain.model.Role;
import com.marcus.user.domain.model.User;
import com.marcus.user.infrastructure.jpa.entity.UserEntity;
import com.marcus.user.infrastructure.jpa.repository.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultAuthenticationService implements AuthenticationService {
  private final UserJpaRepository userJpaRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  @Override
  public User register(RegisterRequest request) {

    if (userJpaRepository.emailRegistered(request.email())) {
      throw new UserAlreadyRegisteredException(request.email());
    }

    UserEntity user =
        UserEntity.builder()
            .name(request.name())
            .surname(request.surname())
            .email(request.email())
            .password(passwordEncoder.encode(request.password()))
            .role(Role.USER)
            .build();
    UserEntity createdUser = userJpaRepository.save(user);
    return new User(
        createdUser.getId(),
        createdUser.getEmail(),
        createdUser.getName(),
        createdUser.getSurname());
  }

  @Override
  public AuthenticationResponse authenticate(AuthenticationRequest request) {

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.email(), request.password()));
    UserEntity user =
        userJpaRepository
            .findByEmail(request.email())
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "User with email: " + request.email() + " does not exist"));
    String jwtToken = jwtService.generateToken(user);
    String refreshToken = jwtService.generateRefreshToken(user);
    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
        .refreshToken(refreshToken)
        .build();
  }
}
