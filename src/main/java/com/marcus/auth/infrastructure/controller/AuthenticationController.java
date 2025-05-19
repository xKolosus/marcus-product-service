package com.marcus.auth.infrastructure.controller;

import com.marcus.auth.domain.service.AuthenticationService;
import com.marcus.auth.infrastructure.controller.in.AuthenticationRequest;
import com.marcus.auth.infrastructure.controller.in.RegisterRequest;
import com.marcus.auth.infrastructure.controller.out.AuthenticationResponse;
import com.marcus.user.domain.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication endpoints")
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/register")
  @Operation(
      description = "Endpoint that'll allow a new user to be registered in our database.",
      summary =
          "This generates a new user row in our database, a simple user, no admin is allowed to be created by hand.")
  public ResponseEntity<User> register(@RequestBody @Valid RegisterRequest request) {
    return ResponseEntity.ok(service.register(request));
  }

  @PostMapping("/authenticate")
  @Operation(
      description = "Endpoint that through an email and password will retrieve a token.",
      summary =
          "Retrieves an access token that last till 1 hour, and a refresh token that works a little bit more.")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody @Valid AuthenticationRequest request) {
    return ResponseEntity.ok(service.authenticate(request));
  }

  @PostMapping("/refresh-token")
  @Operation(
      description = "Endpoint that would invalidate previous token, and generate a new one.",
      summary =
          """
      This endpoint is not necessary really in this use case, but if we would audit the tokens or something like that,
      we would be allowed to only set the last one active, and the others won't work anymore.
      """)
  public void refreshToken(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    service.refreshToken(request, response);
  }
}
