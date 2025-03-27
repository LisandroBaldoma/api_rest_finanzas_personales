package com.apirest.finanzaspersonales.controller.category.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CategoryResponse {
    private Long id;
    private String name;
    private String description;
    private Long parentCategoryId;

    public CategoryResponse(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public CategoryResponse(String name, String description, Long parentCategoryId) {
        this.name = name;
        this.description = description;
        this.parentCategoryId = parentCategoryId;
    }
}
