package com.apirest.finanzaspersonales.service.category.Impl;

import com.apirest.finanzaspersonales.controller.category.model.request.CategoryRequest;
import com.apirest.finanzaspersonales.controller.category.model.response.CategoryResponse;
import com.apirest.finanzaspersonales.entity.Category;
import com.apirest.finanzaspersonales.repository.category.CategoryRepository;
import com.apirest.finanzaspersonales.repository.expense.ExpenseRepository;
import com.apirest.finanzaspersonales.utils.mappers.CategoryMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("createCategory")
    void createCategory_WhenNameIsUnique_ShouldReturnSavedCategory() {
        // Given
        CategoryRequest request = new CategoryRequest("Comida", "Gastos en alimentos", null);
        Category savedCategory = new Category(1L, "comida", "Gastos en alimentos", true, null);
        CategoryResponse expectedResponse = new CategoryResponse( "comida", "Gastos en alimentos",  null);

        // When
        when(categoryRepository.existsByNameAndParentCategoryId("comida", null)).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);
        when(categoryMapper.toResponse(savedCategory)).thenReturn(expectedResponse);

        CategoryResponse result = categoryService.createCategory(request);

        // Then
        assertNotNull(result);
        assertEquals("Gastos en alimentos", result.getDescription());
        assertEquals("comida", result.getName());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    @DisplayName("getCategoryById")
    void getCategoryById_WhenCategoryExists_ShouldReturnResponse() {
        // Arrange
        Long categoryId = 1L;
        Category category = new Category(categoryId, "comida", "Alimentos", true, null);
        CategoryResponse expectedResponse = new CategoryResponse("comida", "Alimentos");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryMapper.toResponse(category)).thenReturn(expectedResponse);

        // Act
        CategoryResponse result = categoryService.getCategoryById(categoryId);

        // Assert
        assertEquals("comida", result.getName());
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void deleteCategory_WhenNoSubcategoriesOrExpenses_ShouldDelete() {
        // Arrange
        Long categoryId = 1L;
        Category category = new Category(categoryId, "comida", "Alimentos", true, null);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryRepository.findByParentCategoryId(categoryId)).thenReturn(List.of());
        when(expenseRepository.existsByCategoryId(categoryId)).thenReturn(false);

        // Act & Assert (No debe lanzar excepciones)
        assertDoesNotThrow(() -> categoryService.deleteCategory(categoryId));
        verify(categoryRepository, times(1)).delete(category);
    }


}