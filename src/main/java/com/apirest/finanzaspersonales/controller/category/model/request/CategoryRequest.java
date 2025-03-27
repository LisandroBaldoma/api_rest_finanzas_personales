package com.apirest.finanzaspersonales.controller.category.model.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CategoryRequest {
    private Long id;
    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String name;

    @NotBlank(message = "La descripción  es obligatorio")
    @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres")
    private String description;

    private boolean isActive;
    private Long parentCategory;

    public CategoryRequest(String name, String description, Long parentCategory) {
        this.name = name;
        this.description = description;
        this.parentCategory = parentCategory;
    }

}
