package com.marcus.user.domain.mapper;

import com.marcus.user.domain.model.User;
import com.marcus.user.infrastructure.jpa.entity.UserEntity;
import java.util.List;

public class UserMapper {

  public static User toDomain(UserEntity entity) {

    return new User(entity.getId(), entity.getEmail(), entity.getName(), entity.getSurname());
  }

  public static List<User> toDomain(List<UserEntity> entity) {

    return entity.stream().map(UserMapper::toDomain).toList();
  }
}
