package com.apirest.finanzaspersonales.repository.category;

import com.apirest.finanzaspersonales.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByIsActive(boolean isActive); // Obtener solo categorías activas
}
