package com.apirest.finanzaspersonales.service.category.Impl;

import com.apirest.finanzaspersonales.controller.category.model.request.CategoryRequest;
import com.apirest.finanzaspersonales.controller.category.model.response.CategoryResponse;
import com.apirest.finanzaspersonales.entity.Category;
import com.apirest.finanzaspersonales.exceptions.BadRequestException;
import com.apirest.finanzaspersonales.exceptions.AlreadyExistsException;
import com.apirest.finanzaspersonales.repository.category.CategoryRepository;
import com.apirest.finanzaspersonales.repository.expense.ExpenseRepository;
import com.apirest.finanzaspersonales.service.category.CategoryService;
import com.apirest.finanzaspersonales.utils.mappers.CategoryMapper;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ExpenseRepository expenseRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper, ExpenseRepository expenseRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.expenseRepository = expenseRepository;
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Long parentId = categoryRequest.getParentCategory();

        // Convertir el nombre a minúsculas antes de validarlo y guardarlo
        String categoryName = categoryRequest.getName().toLowerCase();

        // Validar si el nombre ya existe en el mismo nivel
        boolean exists = categoryRepository.existsByNameAndParentCategoryId(categoryName, parentId);
        if (exists) {
            throw new AlreadyExistsException("Ya existe una categoría con este nombre en la misma categoría padre.");
        }

        // Validar si el parentCategoryId existe
        Category parentCategory = null;
        if (parentId != null) {
            parentCategory = categoryRepository.findById(parentId)
                    .orElseThrow(() -> new AlreadyExistsException("El parentCategoryId especificado no existe."));
        }

        // Crear la nueva categoría con valores corregidos
        Category newCategory = new Category();
        newCategory.setName(categoryName);
        newCategory.setDescription(categoryRequest.getDescription());
        newCategory.setActive(true);
        newCategory.setParentCategory(parentCategory);

        return categoryMapper.toResponse(categoryRepository.save(newCategory));
    }

    @Override
    public List<CategoryResponse> getActiveCategories() {
        List<Category> activeCategories = categoryRepository.findByIsActiveTrue();
        return activeCategories.stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AlreadyExistsException("Categoría no encontrada"));
        return categoryMapper.toResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AlreadyExistsException("Categoría con ID " + id + " no encontrada"));

        Long newParentId = categoryRequest.getParentCategory();

        // Validar si el nuevo nombre ya existe en el mismo nivel, excluyendo la misma categoría
        boolean nameExists = categoryRepository.existsByNameAndParentCategoryId(categoryRequest.getName(), newParentId);
        if (nameExists && !category.getName().equalsIgnoreCase(categoryRequest.getName())) {
            throw new AlreadyExistsException("Ya existe una categoría con este nombre en la misma categoría padre.");
        }

        // Verificar si se está intentando asignar como su propio padre
        if (newParentId != null && newParentId.equals(id)) {
            throw new AlreadyExistsException("Una categoría no puede ser su propio padre.");
        }

        // Verificar que el nuevo parentCategory exista si se está actualizando
        Category newParentCategory = null;
        if (newParentId != null) {
            newParentCategory = categoryRepository.findById(newParentId)
                    .orElseThrow(() -> new AlreadyExistsException("El parentCategoryId especificado no existe."));
        }

        // Validar que no genere un ciclo (Ejemplo: A → B → C → A)
        if (isCyclicRelationship(category, newParentCategory)) {
            throw new AlreadyExistsException("No se puede crear una relación cíclica.");
        }

        // Aplicar actualizaciones solo si hay cambios
        if (!category.getName().equals(categoryRequest.getName())) {
            category.setName(categoryRequest.getName());
        }
        if (!Objects.equals(category.getDescription(), categoryRequest.getDescription())) {
            category.setDescription(categoryRequest.getDescription());
        }
        if (category.isActive() != categoryRequest.isActive()) {
            category.setActive(categoryRequest.isActive());
        }
        if (!Objects.equals(category.getParentCategory(), newParentCategory)) {
            category.setParentCategory(newParentCategory);
        }

        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) {
        // Verificar si la categoría existe
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AlreadyExistsException("Categoría con ID " + id + " no encontrada"));

        // Verificar si la categoría tiene subcategorías activas
        List<Category> subcategories = categoryRepository.findByParentCategoryId(id);
        if (!subcategories.isEmpty()) {
            throw new BadRequestException("La categoría tiene subcategorías y no se puede eliminar hasta que no tenga subcategorías.");
        }

        // Verificar si la categoría está asociada a algún gasto
        boolean hasExpenses = expenseRepository.existsByCategoryId(id);
        if (hasExpenses) {
            throw new BadRequestException("La categoría no puede eliminarse porque tiene gastos asociados.");
        }

        // Eliminar la categoría
        categoryRepository.delete(category);
    }

    private boolean isCyclicRelationship(Category category, Category newParentCategory) {
        Category current = newParentCategory;
        while (current != null) {
            if (current.getId().equals(category.getId())) {
                return true; // Se detectó un ciclo
            }
            current = current.getParentCategory();
        }
        return false;
    }
}
