package com.apirest.finanzaspersonales.service.expense.Impl;

import com.apirest.finanzaspersonales.controller.expense.model.request.ExpenseRequest;
import com.apirest.finanzaspersonales.controller.expense.model.response.ExpenseResponse;
import com.apirest.finanzaspersonales.entity.Category;
import com.apirest.finanzaspersonales.entity.Expense;
import com.apirest.finanzaspersonales.entity.User;
import com.apirest.finanzaspersonales.repository.category.CategoryRepository;
import com.apirest.finanzaspersonales.repository.expense.ExpenseRepository;
import com.apirest.finanzaspersonales.repository.user.UserRepository;
import com.apirest.finanzaspersonales.utils.mappers.ExpenseMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceImplTest {

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    @Mock
    private ExpenseRepository expenseRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private ExpenseMapper expenseMapper;

    @Test
    void getExpenses() {
        // Arrange
        Expense expense = new Expense();
        List<Expense> expenses = List.of(expense);
        ExpenseResponse expenseResponse = new ExpenseResponse();

        when(expenseRepository.findAll()).thenReturn(expenses);
        when(expenseMapper.mapToResponse(expense)).thenReturn(expenseResponse);

        // Act
        List<ExpenseResponse> result = expenseService.getExpenses();

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(expenseRepository).findAll();
    }

    @Test
    void getExpenseById() {
        // Arrange
        Long id = 1L;
        Expense expense = new Expense();
        ExpenseResponse response = new ExpenseResponse();

        when(expenseRepository.findById(id)).thenReturn(Optional.of(expense));
        when(expenseMapper.mapToResponse(expense)).thenReturn(response);

        // Act
        ExpenseResponse result = expenseService.getExpenseById(id);

        // Assert
        assertNotNull(result);
        verify(expenseRepository).findById(id);
    }

    @Test
    void createExpense() {
        // Arrange
        ExpenseRequest request = new ExpenseRequest();
        request.setUserId(1L);
        request.setCategoryId(2L);

        User user = new User();
        Category category = new Category();
        Expense expense = new Expense();
        Expense savedExpense = new Expense();
        ExpenseResponse response = new ExpenseResponse();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(category));
        when(expenseMapper.mapToEntity(any(), any(), any(), any(), any(), any())).thenReturn(expense);
        when(expenseRepository.save(expense)).thenReturn(savedExpense);
        when(expenseMapper.mapToResponse(savedExpense)).thenReturn(response);

        // Act
        ExpenseResponse result = expenseService.createExpense(request);

        // Assert
        assertNotNull(result);
        verify(expenseRepository).save(expense);
    }

    @Test
    void deleteExpense() {
        // Arrange
        Long id = 1L;
        Expense expense = new Expense();

        when(expenseRepository.findById(id)).thenReturn(Optional.of(expense));
        doNothing().when(expenseRepository).delete(expense);

        // Act
        expenseService.deleteExpense(id);

        // Assert
        verify(expenseRepository).delete(expense);
    }
}