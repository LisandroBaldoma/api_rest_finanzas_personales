package com.apirest.finanzaspersonales.utils.mappers;

import com.apirest.finanzaspersonales.controller.category.model.request.CategoryRequest;
import com.apirest.finanzaspersonales.controller.category.model.response.CategoryResponse;
import com.apirest.finanzaspersonales.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequest request, Category parentCategory) {
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setParentCategory(parentCategory);
        return category;
    }

    public CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getName(),
                category.getDescription(),
                (category.getParentCategory() != null) ? category.getParentCategory().getId() : null
        );
    }
}
