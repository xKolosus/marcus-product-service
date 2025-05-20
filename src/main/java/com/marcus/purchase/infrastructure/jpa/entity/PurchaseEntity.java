package com.marcus.purchase.infrastructure.jpa.entity;

import com.marcus.product.infrastructure.jpa.entity.ProductEntity;
import com.marcus.user.infrastructure.jpa.entity.UserEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "purchase")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne private UserEntity user;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "purchase")
  private List<PurchaseDetailEntity> details = new ArrayList<>();

  @Transient
  public List<ProductEntity> getProductsBought() {

    return details.stream().map(PurchaseDetailEntity::getProduct).toList();
  }

  public void addDetail(ProductEntity product) {
    PurchaseDetailEntity detail = new PurchaseDetailEntity(this, product);
    details.add(detail);
  }
}
