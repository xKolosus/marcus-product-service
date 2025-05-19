package com.marcus.user.domain.service;

import com.marcus.pagination.domain.model.Page;
import com.marcus.user.domain.model.User;
import com.marcus.user.infrastructure.controller.in.UserRequest;
import java.util.UUID;

public interface UserService {

  void deleteUserById(UUID userId);

  Page<User> getUsers(UserRequest request);
}
