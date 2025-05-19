package com.marcus.product.infrastructure.jpa.entity;

import com.marcus.subcategory.infrastructure.jpa.entity.SubCategoryEntity;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;

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
}
