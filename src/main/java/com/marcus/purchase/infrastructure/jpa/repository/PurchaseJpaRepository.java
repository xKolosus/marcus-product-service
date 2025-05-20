package com.marcus.purchase.infrastructure.jpa.repository;

import com.marcus.purchase.infrastructure.jpa.entity.PurchaseEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseJpaRepository
    extends JpaRepository<PurchaseEntity, UUID>, JpaSpecificationExecutor<PurchaseEntity> {}
