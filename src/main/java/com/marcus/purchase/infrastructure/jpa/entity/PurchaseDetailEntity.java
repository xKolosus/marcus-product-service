package com.marcus.purchase.infrastructure.jpa.entity;

import com.marcus.product.infrastructure.jpa.entity.ProductEntity;
import com.marcus.purchase.infrastructure.jpa.entity.id.PurchaseDetailId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "purchase_detail")
@Getter
@Setter
@NoArgsConstructor
public class PurchaseDetailEntity {

  @EmbeddedId private PurchaseDetailId id;

  @MapsId("purchaseId")
  @ManyToOne
  @JoinColumn(
      name = "purchase_id",
      referencedColumnName = "id",
      nullable = false,
      updatable = false)
  private PurchaseEntity purchase;

  @MapsId("productId")
  @ManyToOne
  @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false, updatable = false)
  private ProductEntity product;

  public PurchaseDetailEntity(PurchaseEntity purchase, ProductEntity product) {
    this.purchase = purchase;
    this.product = product;
    this.id = new PurchaseDetailId(purchase.getId(), product.getId());
  }
}
