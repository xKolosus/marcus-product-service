package com.marcus.user.infrastructure.jpa.mapper;

import com.marcus.pagination.domain.model.Page;
import com.marcus.pagination.domain.model.PaginationMapper;
import com.marcus.user.domain.model.User;
import com.marcus.user.infrastructure.jpa.entity.UserEntity;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapper implements PaginationMapper<User, UserEntity> {

  @Override
  public Page<User> toDomain(
      org.springframework.data.domain.Page<UserEntity> page,
      Function<UserEntity, User> contentMapping) {
    return new Page<>(
        page.getContent().stream().map(contentMapping).toList(), PaginationMapper.toDomain(page));
  }
}
