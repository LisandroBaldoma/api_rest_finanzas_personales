package com.apirest.finanzaspersonales.utils.mappers;

import com.apirest.finanzaspersonales.controller.expense.model.request.ExpenseRequest;
import com.apirest.finanzaspersonales.controller.expense.model.response.ExpenseResponse;
import com.apirest.finanzaspersonales.entity.Category;
import com.apirest.finanzaspersonales.entity.Expense;
import com.apirest.finanzaspersonales.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ExpenseMapperTest {
    private ExpenseMapper expenseMapper;

    @BeforeEach
    void setUp() {
        expenseMapper = new ExpenseMapper(); // No usa dependencias, no necesita @Mock
    }

    @Test
    void mapToEntity_ShouldMapCorrectly() {

    }

    @Test
    void mapToResponse_ShouldMapCorrectly() {

    }
}