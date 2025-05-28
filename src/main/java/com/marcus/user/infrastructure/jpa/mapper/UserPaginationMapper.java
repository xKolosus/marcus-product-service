package com.marcus.user.infrastructure.jpa.mapper;

import com.marcus.pagination.domain.model.Pageable;
import com.marcus.pagination.mapper.PaginationMapper;
import com.marcus.user.domain.model.User;
import com.marcus.user.infrastructure.jpa.entity.UserEntity;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class UserPaginationMapper implements PaginationMapper<User, UserEntity> {

  @Override
  public Pageable<User> toDomain(
      org.springframework.data.domain.Page<UserEntity> page,
      Function<UserEntity, User> contentMapping) {
    return new Pageable<>(
        page.getContent().stream().map(contentMapping).toList(), PaginationMapper.toDomain(page));
  }
}
