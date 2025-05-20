package com.marcus.product.infrastructure.jpa.entity;

import com.marcus.subcategory.infrastructure.jpa.entity.SubCategoryEntity;
import io.hypersistence.utils.hibernate.type.money.MonetaryAmountType;
import jakarta.persistence.*;
import java.util.UUID;
import javax.money.MonetaryAmount;
import lombok.*;
import org.hibernate.annotations.CompositeType;

@Entity
@Table(name = "product")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "photo_url")
  private String photoUrl;

  @ManyToOne private SubCategoryEntity subCategory;

  @Column(name = "stock")
  private long stock;

  @AttributeOverride(name = "amount", column = @Column(name = "amount"))
  @AttributeOverride(name = "currency", column = @Column(name = "currency"))
  @CompositeType(MonetaryAmountType.class)
  private MonetaryAmount price;
}
