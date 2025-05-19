package com.marcus.category.infrastructure.jpa.repository;

import com.marcus.category.infrastructure.jpa.entity.CategoryEntity;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, UUID> {

  @Transactional
  @Modifying
  @Query(
      """
    update CategoryEntity set enabled = false where id = :categoryId
    """)
  void disableCategory(UUID categoryId);
}
