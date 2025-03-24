package com.apirest.finanzaspersonales.service.category;

import com.apirest.finanzaspersonales.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Category saveCategory(Category category);
    Optional<Category> getCategoryById(Long id);
    List<Category> getAllCategories();
    List<Category> getActiveCategories();
    void deleteCategory(Long id);
}
