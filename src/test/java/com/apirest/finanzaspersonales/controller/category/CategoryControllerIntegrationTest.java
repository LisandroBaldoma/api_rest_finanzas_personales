package com.apirest.finanzaspersonales.controller.category;

import com.apirest.finanzaspersonales.controller.category.model.request.CategoryRequest;
import com.apirest.finanzaspersonales.controller.category.model.response.CategoryResponse;
import com.apirest.finanzaspersonales.service.category.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;  // Simula peticiones HTTP

    @Autowired
    private ObjectMapper objectMapper;  // Para convertir objetos a JSON

    @MockitoBean
    private CategoryService categoryService;  // Mock del servicio principal

    private CategoryRequest request1;
    private CategoryResponse response1;

    @BeforeEach
    void setUp() {
        // Preparamos datos de prueba
        request1 = new CategoryRequest("Category 1", "Description", null);
        response1 = new CategoryResponse(1L, "Category 1", "Description", null);
    }

    // --- Tests para cada endpoint ---

    @Test
    @DisplayName("GET /api/v1/categories - Debe retornar 200 y lista de categorías")
    void getAllCategories_ShouldReturnCategories() throws Exception {
        // Datos de prueba
        CategoryResponse category1 = new CategoryResponse(1L, "Category 1", "Description", null);
        CategoryResponse category2 = new CategoryResponse(2L, "Category 2", "Description", null);
        List<CategoryResponse> categories = List.of(category1, category2);

        // Mock del servicio
        when(categoryService.getAllCategories()).thenReturn(categories);

        // Ejecutar petición GET y verificar respuesta
        mockMvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Category 1"));
    }

    @Test
    @DisplayName("GET /api/v1/categories/{id} - Debe retornar 200 si la categoría existe")
    void getCategoryById_WhenCategoryExists_ShouldReturnCategory() throws Exception {
        Long categoryId = 1L;
        CategoryResponse category = new CategoryResponse(1L, "Category 1", "Description", null);

        when(categoryService.getCategoryById(categoryId)).thenReturn(category);

        mockMvc.perform(get("/api/v1/categories/{id}", categoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Category 1"));
    }

    @Test
    @DisplayName("POST /api/v1/categories - Debe retornar 201 y la categoría creada")
    void createCategory_ShouldReturnCreatedCategory() throws Exception {
        when(categoryService.createCategory(any())).thenReturn(response1);

        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Category 1"))
                .andExpect(jsonPath("$.description").value("Description"));

        verify(categoryService, times(1)).createCategory(any());
    }

    @Test
    @DisplayName("PUT /api/v1/categories/{id} - Debe retornar 200 y la categoría actualizada")
    void updateCategory_ShouldReturnUpdatedCategory() throws Exception {
        Long categoryId = 1L;
        CategoryRequest request = new CategoryRequest("Updated Category", "Updated Description", null);
        CategoryResponse response = new CategoryResponse(1L, "Updated Category", "Updated Description", null);

        when(categoryService.updateCategory(eq(categoryId), any(CategoryRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/v1/categories/{id}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Category"))
                .andExpect(jsonPath("$.description").value("Updated Description"));

        verify(categoryService, times(1)).updateCategory(eq(categoryId), any(CategoryRequest.class));
    }

    @Test
    @DisplayName("DELETE /api/v1/categories/{id} - Debe retornar 200")
    void deleteCategory_ShouldReturnNoContent() throws Exception {
        Long categoryId = 1L;

        mockMvc.perform(delete("/api/v1/categories/{id}", categoryId))
                .andExpect(status().isOk())
                .andExpect(content().string("Categoría eliminada correctamente."));

        verify(categoryService, times(1)).deleteCategory(categoryId);  // Verifica que se llamó al servicio
    }

    @Test
    @DisplayName("GET /api/v1/categories/active - Debe retornar 200 y lista de categorías activas")
    void getActiveCategories_ShouldReturnActiveCategories() throws Exception {
        CategoryResponse category1 = new CategoryResponse(1L, "Active Category 1", "Description", null);
        CategoryResponse category2 = new CategoryResponse(2L, "Active Category 2", "Description", null);
        List<CategoryResponse> activeCategories = List.of(category1, category2);

        when(categoryService.getActiveCategories()).thenReturn(activeCategories);

        mockMvc.perform(get("/api/v1/categories/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Active Category 1"));
    }
}