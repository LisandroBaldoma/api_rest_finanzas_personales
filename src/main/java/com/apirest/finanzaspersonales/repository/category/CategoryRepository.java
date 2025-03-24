package com.apirest.finanzaspersonales.repository.category;

import com.apirest.finanzaspersonales.controller.category.model.response.CategoryResponse;
import com.apirest.finanzaspersonales.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Obtener solo categorías activas
    List<Category> findByIsActiveTrue();

    // Buscar subcategorías por ID de la categoría principal
    List<Category> findByParentCategoryId(Long parentCategoryId);

    // Método para verificar si ya existe una categoría con el mismo nombre y parentCategory
    boolean existsByNameAndParentCategoryId(String name, Long parentCategoryId);


}


