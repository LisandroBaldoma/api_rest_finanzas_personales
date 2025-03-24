package com.apirest.finanzaspersonales.service.expense;

import com.apirest.finanzaspersonales.entity.Expense;

import java.util.List;
import java.util.Optional;

public interface ExpenseService {
    Expense saveExpense(Expense expense);
    Optional<Expense> getExpenseById(Long id);
    List<Expense> getAllExpenses();
    void deleteExpense(Long id);
}
