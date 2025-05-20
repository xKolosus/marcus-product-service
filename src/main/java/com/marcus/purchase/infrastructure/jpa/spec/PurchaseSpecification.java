package com.marcus.purchase.infrastructure.jpa.spec;

import com.marcus.purchase.infrastructure.jpa.criteria.PurchaseCriteria;
import com.marcus.purchase.infrastructure.jpa.entity.PurchaseEntity;
import com.marcus.user.infrastructure.jpa.entity.UserEntity;
import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class PurchaseSpecification implements Specification<PurchaseEntity> {

  private transient PurchaseCriteria criteria;

  @Override
  public Predicate toPredicate(
      Root<PurchaseEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    List<Predicate> predicates = new ArrayList<>();
    if (criteria.userId() != null) {
      Join<UserEntity, PurchaseEntity> joinUser = root.join("user");
      predicates.add(cb.equal(joinUser.get("id"), criteria.userId()));
    }

    return cb.and(predicates.toArray(Predicate[]::new));
  }
}
