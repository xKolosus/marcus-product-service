package com.marcus.category.infrastructure.jpa.entity;

import com.marcus.subcategory.infrastructure.jpa.entity.SubCategoryEntity;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "category")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "enabled")
    private boolean enabled;

  @OneToMany(mappedBy = "category")
  List<SubCategoryEntity> subCategories;
}
