package com.marcus.user.infrastructure.jpa.spec;

import com.marcus.user.infrastructure.jpa.criteria.UserCriteria;
import com.marcus.user.infrastructure.jpa.entity.UserEntity;
import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class UserSpecification implements Specification<UserEntity> {

  private static final String LIKE_PATTERN = "%";
  private transient UserCriteria criteria;

  @Override
  public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

    List<Predicate> predicates = new ArrayList<>();

    if (criteria.searchBy() != null) {

      Expression<String> nameExpression =
          cb.concat(cb.concat(root.get("name"), " "), root.get("surname"));
      predicates.add(
          cb.or(
              cb.like(
                  cb.lower(root.get("name")),
                  LIKE_PATTERN + criteria.searchBy().toLowerCase() + LIKE_PATTERN),
              cb.like(
                  cb.lower(root.get("surname")),
                  LIKE_PATTERN + criteria.searchBy().toLowerCase() + LIKE_PATTERN),
              cb.like(
                  cb.lower(root.get("email")),
                  LIKE_PATTERN + criteria.searchBy().toLowerCase() + LIKE_PATTERN),
              cb.like(
                  cb.lower(nameExpression),
                  LIKE_PATTERN + criteria.searchBy().toLowerCase() + LIKE_PATTERN)));
    }

    return cb.and(predicates.toArray(Predicate[]::new));
  }
}
