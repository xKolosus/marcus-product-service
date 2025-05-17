package com.marcus.category.application;

import com.marcus.category.domain.mapper.CategoryMapper;
import com.marcus.category.domain.model.Category;
import com.marcus.category.domain.service.CategoryService;
import com.marcus.category.infrastructure.jpa.repository.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultCategoryService implements CategoryService {

    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public List<Category> findAll() {

        return CategoryMapper.toDomain(categoryJpaRepository.findAll());
    }
}
