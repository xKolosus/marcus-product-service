package com.marcus.category.infrastructure.jpa.repository;

import com.marcus.category.infrastructure.jpa.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, UUID> {
}
