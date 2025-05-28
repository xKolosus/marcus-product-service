package com.marcus.auth.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.marcus.auth.application.auxiliar.JwtService;
import com.marcus.auth.application.exception.UserAlreadyRegisteredException;
import com.marcus.auth.infrastructure.controller.in.AuthenticationRequest;
import com.marcus.auth.infrastructure.controller.in.RegisterRequest;
import com.marcus.auth.infrastructure.controller.out.AuthenticationResponse;
import com.marcus.user.domain.model.User;
import com.marcus.user.infrastructure.jpa.entity.UserEntity;
import com.marcus.user.infrastructure.jpa.repository.UserJpaRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class DefaultAuthenticationServiceTest {

  @InjectMocks private DefaultAuthenticationService service;

  @Mock private UserJpaRepository userJpaRepository;

  @Mock private PasswordEncoder passwordEncoder;

  @Mock private JwtService jwtService;

  @Mock private AuthenticationManager authenticationManager;

  @Test
  void register_thenEmailExists_thenThrowException() {

    RegisterRequest request = new RegisterRequest("a", "b", "ab@gmail.com", "123");

    when(userJpaRepository.emailRegistered(any())).thenReturn(true);

    UserAlreadyRegisteredException e =
        assertThrows(UserAlreadyRegisteredException.class, () -> service.register(request));

    assertEquals("Email: " + request.email() + " already registered.", e.getMessage());
  }

  @Test
  void register_thenEmailDoesNotExist_thenSave() {

    RegisterRequest request = new RegisterRequest("a", "b", "ab@gmail.com", "123");

    when(userJpaRepository.emailRegistered(any())).thenReturn(false);
    when(userJpaRepository.save(any()))
        .thenReturn(
            UserEntity.builder()
                .name(request.name())
                .surname(request.surname())
                .email(request.email())
                .build());

    User user = service.register(request);

    assertNotNull(user);
    assertEquals(request.email(), user.email());
    verify(passwordEncoder, atLeastOnce()).encode(request.password());
    verify(userJpaRepository, atLeastOnce()).save(any());
  }

  @Test
  void authenticate() {

    AuthenticationRequest request = new AuthenticationRequest("ab@gmail.com", "123");

    when(userJpaRepository.findByEmail(any()))
        .thenReturn(
            Optional.of(
                UserEntity.builder().name("a").surname("b").email(request.email()).build()));
    when(jwtService.generateToken(any())).thenReturn("token");
    when(jwtService.generateRefreshToken(any())).thenReturn("refreshToken");

    AuthenticationResponse response = service.authenticate(request);

    assertNotNull(response);
    assertEquals("token", response.getAccessToken());
  }
}
