package com.marcus.product.infrastructure.jpa.repository;

import com.marcus.product.infrastructure.jpa.entity.ProductEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJpaRepository
    extends JpaRepository<ProductEntity, UUID>, JpaSpecificationExecutor<ProductEntity> {}
