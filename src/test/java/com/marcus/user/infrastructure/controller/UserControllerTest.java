package com.marcus.user.infrastructure.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.marcus.pagination.domain.model.PageCount;
import com.marcus.pagination.domain.model.Pageable;
import com.marcus.user.domain.model.User;
import com.marcus.user.domain.service.UserService;
import com.marcus.user.infrastructure.controller.in.UserRequest;
import java.util.List;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

  @InjectMocks private UserController controller;

  @Mock private UserService userService;

  @Test
  void searchUser() {

    List<User> users = Instancio.createList(User.class);
    Pageable<User> domain =
        new Pageable<>(users, new PageCount(15, users.size(), users.size(), 0, 1));

    when(userService.getUsers(any())).thenReturn(domain);

    ResponseEntity<Pageable<User>> response =
        controller.searchUser(Instancio.create(UserRequest.class));

    assertNotNull(response.getBody());
    assertEquals(users.size(), response.getBody().getContent().size());
  }
}
