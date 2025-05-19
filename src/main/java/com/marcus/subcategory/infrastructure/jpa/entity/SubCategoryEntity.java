package com.marcus.subcategory.infrastructure.jpa.entity;

import com.marcus.category.infrastructure.jpa.entity.CategoryEntity;
import com.marcus.product.infrastructure.jpa.entity.ProductEntity;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "sub_category")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubCategoryEntity {

  @OneToMany List<ProductEntity> products;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @ManyToOne private CategoryEntity category;
}
