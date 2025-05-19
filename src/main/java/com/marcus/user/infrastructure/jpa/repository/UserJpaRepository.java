package com.marcus.user.infrastructure.jpa.repository;

import com.marcus.user.infrastructure.jpa.entity.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository
    extends JpaRepository<UserEntity, UUID>, JpaSpecificationExecutor<UserEntity> {

  Optional<UserEntity> findByEmail(String email);

  @Query(
      value =
          """
        select exists(select 1 from UserEntity u where u.email = :email)
        """)
  boolean emailRegistered(String email);
}
