package com.marcus.subcategory.infrastructure.jpa.repository;

import com.marcus.subcategory.infrastructure.jpa.entity.SubCategoryEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryJpaRepository extends JpaRepository<SubCategoryEntity, UUID> {}
