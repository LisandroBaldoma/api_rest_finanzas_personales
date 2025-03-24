package com.apirest.finanzaspersonales.service.category;

import com.apirest.finanzaspersonales.controller.category.model.request.CategoryRequest;
import com.apirest.finanzaspersonales.controller.category.model.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);
    CategoryResponse getCategoryById(Long id);
    List<CategoryResponse> getAllCategories();
    CategoryResponse updateCategory(Long id, CategoryRequest request);
    void deleteCategory(Long id);
    List<CategoryResponse> getActiveCategories();
}
