package com.marcus.user.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.marcus.user.domain.model.User;
import com.marcus.user.infrastructure.controller.in.UserRequest;
import com.marcus.user.infrastructure.jpa.entity.UserEntity;
import com.marcus.user.infrastructure.jpa.mapper.UserPaginationMapper;
import com.marcus.user.infrastructure.jpa.repository.UserJpaRepository;
import com.marcus.user.infrastructure.jpa.spec.UserSpecification;
import java.util.List;
import java.util.UUID;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class DefaultUserServiceTest {

  @InjectMocks private DefaultUserService service;

  @Mock private UserJpaRepository userJpaRepository;

  @Mock(answer = Answers.CALLS_REAL_METHODS)
  private UserPaginationMapper userPaginationMapper;

  @Test
  void deleteUserById() {

    when(userJpaRepository.existsById(any())).thenReturn(true);

    UUID userId = UUID.randomUUID();
    service.deleteUserById(userId);

    verify(userJpaRepository, atLeastOnce()).deleteById(userId);
  }

  @Test
  void getUsers() {

    UserRequest request = Instancio.create(UserRequest.class);
    List<UserEntity> users = Instancio.createList(UserEntity.class);
    Page<UserEntity> page = new PageImpl<>(users, PageRequest.of(0, 15), users.size());

    when(userJpaRepository.findAll(any(UserSpecification.class), any(Pageable.class)))
        .thenReturn(page);

    com.marcus.pagination.domain.model.Pageable<User> domain = service.getUsers(request);

    assertNotNull(domain);
    assertEquals(users.size(), domain.getPage().totalContent());
  }
}
