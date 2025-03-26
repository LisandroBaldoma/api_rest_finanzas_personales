package com.apirest.finanzaspersonales.service.expense;

import com.apirest.finanzaspersonales.controller.expense.model.request.ExpenseRequest;
import com.apirest.finanzaspersonales.controller.expense.model.response.ExpenseResponse;

import java.util.List;


public interface ExpenseService {

    List<ExpenseResponse> getExpenses();

    ExpenseResponse getExpenseById(Long expenseId);

    ExpenseResponse createExpense(ExpenseRequest request);

    ExpenseResponse updateExpense(Long expenseId, ExpenseRequest request);

    void deleteExpense(Long expenseId);

}
