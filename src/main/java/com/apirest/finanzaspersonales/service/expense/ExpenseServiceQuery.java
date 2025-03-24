package com.apirest.finanzaspersonales.service.expense;

import com.apirest.finanzaspersonales.entity.Expense;

import java.util.List;
import java.util.Optional;

public interface ExpenseServiceQuery {
    List<Expense> getExpensesByUser(Long userId);
    List<Expense> getExpensesByCategory(String category);
    double getTotalExpensesByUser(Long userId);
}
