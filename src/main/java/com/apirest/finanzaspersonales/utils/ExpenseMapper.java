package com.apirest.finanzaspersonales.utils;

import com.apirest.finanzaspersonales.controller.expense.model.request.ExpenseRequest;
import com.apirest.finanzaspersonales.controller.expense.model.response.ExpenseResponse;

import com.apirest.finanzaspersonales.entity.Category;
import com.apirest.finanzaspersonales.entity.Expense;
import com.apirest.finanzaspersonales.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ExpenseMapper {

    public Expense mapToEntity(ExpenseRequest request, LocalDate date, String currency, String paymentMethod, User user, Category category) {
        return new Expense(
                request.getAmount(),
                date,
                request.getDescription(),
                paymentMethod,
                currency,
                user,
                category
        );
    }

    public ExpenseResponse mapToResponse(Expense expense) {
        return new ExpenseResponse(
                expense.getAmount(),
                expense.getDate(),
                expense.getDescription(),
                expense.getPaymentMethod(),
                expense.getCurrency(),
                expense.getUser().getName(), // Puedes devolver el nombre del usuario
                expense.getCategory().getName() // O el nombre de la categoría
        );
    }
}
