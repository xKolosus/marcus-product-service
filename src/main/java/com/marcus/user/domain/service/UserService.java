package com.marcus.user.domain.service;

import com.marcus.pagination.domain.model.Pageable;
import com.marcus.user.domain.model.User;
import com.marcus.user.infrastructure.controller.in.UserRequest;
import java.util.UUID;

public interface UserService {

  void deleteUserById(UUID userId);

  Pageable<User> getUsers(UserRequest request);
}
