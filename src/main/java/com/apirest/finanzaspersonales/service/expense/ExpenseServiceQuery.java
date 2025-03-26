package com.apirest.finanzaspersonales.service.expense;


import com.apirest.finanzaspersonales.controller.expense.model.response.ExpenseResponse;

import java.util.List;

public interface ExpenseServiceQuery {
    List<ExpenseResponse> getExpensesByUser(Long userId);

    double getTotalSpentByUser(Long userId);
}
