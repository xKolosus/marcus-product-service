package com.marcus.purchase.infrastructure.jpa.entity.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDetailId {
  @Column(name = "purchase_id")
  private UUID purchaseId;

  @Column(name = "product_id")
  private UUID productId;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PurchaseDetailId that)) return false;

    return Objects.equals(getPurchaseId(), that.getPurchaseId())
        && Objects.equals(getProductId(), that.getProductId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getPurchaseId(), getProductId());
  }
}
