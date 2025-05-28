package com.marcus.auth.infrastructure.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.marcus.auth.domain.service.AuthenticationService;
import com.marcus.auth.infrastructure.controller.in.AuthenticationRequest;
import com.marcus.auth.infrastructure.controller.in.RegisterRequest;
import com.marcus.auth.infrastructure.controller.out.AuthenticationResponse;
import com.marcus.user.domain.model.User;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

  @InjectMocks private AuthenticationController controller;

  @Mock private AuthenticationService authenticationService;

  @Test
  void register() {

    User user = new User(UUID.randomUUID(), "a@gmail.com", "b", "a");

    when(authenticationService.register(any())).thenReturn(user);

    ResponseEntity<User> response =
        controller.register(new RegisterRequest("a", "b", "a@gmail.com", "123"));

    assertNotNull(response.getBody());
    assertEquals(user.email(), response.getBody().email());
  }

  @Test
  void authenticate() {

    when(authenticationService.authenticate(any()))
        .thenReturn(new AuthenticationResponse("a", "b"));

    ResponseEntity<AuthenticationResponse> response =
        controller.authenticate(new AuthenticationRequest("a", "123"));

    assertNotNull(response.getBody());
    assertEquals("a", response.getBody().getAccessToken());
  }
}
