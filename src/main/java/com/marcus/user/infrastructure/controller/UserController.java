package com.marcus.user.infrastructure.controller;

import com.marcus.pagination.domain.model.Pageable;
import com.marcus.user.domain.model.User;
import com.marcus.user.domain.service.UserService;
import com.marcus.user.infrastructure.controller.in.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User endpoints (ADMIN ONLY)")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

  private final UserService userService;

  @PostMapping
  @Operation(
      description = "Get all users filtering",
      summary =
          "This will retrieve all the expected users, filtering by a search by and pagination.")
  public ResponseEntity<Pageable<User>> searchUser(@RequestBody UserRequest request) {

    return ResponseEntity.ok(userService.getUsers(request));
  }
}
