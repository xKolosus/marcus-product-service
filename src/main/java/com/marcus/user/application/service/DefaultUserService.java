package com.marcus.user.application.service;

import com.marcus.pagination.domain.model.Page;
import com.marcus.pagination.mapper.PaginationMapper;
import com.marcus.user.domain.mapper.UserMapper;
import com.marcus.user.domain.model.User;
import com.marcus.user.domain.service.UserService;
import com.marcus.user.infrastructure.controller.in.UserRequest;
import com.marcus.user.infrastructure.jpa.criteria.UserCriteria;
import com.marcus.user.infrastructure.jpa.mapper.UserPaginationMapper;
import com.marcus.user.infrastructure.jpa.repository.UserJpaRepository;
import com.marcus.user.infrastructure.jpa.spec.UserSpecification;
import jakarta.persistence.EntityNotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

  private final UserJpaRepository userJpaRepository;
  private final UserPaginationMapper userPaginationMapper;

  @Override
  public void deleteUserById(UUID userId) {

    if (!userJpaRepository.existsById(userId)) {
      throw new EntityNotFoundException("User not found!");
    }

    userJpaRepository.deleteById(userId);
  }

  @Override
  public Page<User> getUsers(UserRequest request) {

    return userPaginationMapper.toDomain(
        userJpaRepository.findAll(
            new UserSpecification(new UserCriteria(request.getSearchBy())),
            PaginationMapper.toPageRequest(request)),
        UserMapper::toDomain);
  }
}
