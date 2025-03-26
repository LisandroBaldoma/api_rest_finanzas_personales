package com.apirest.finanzaspersonales.service.expense.Impl;

import com.apirest.finanzaspersonales.controller.expense.model.response.ExpenseResponse;
import com.apirest.finanzaspersonales.entity.Expense;
import com.apirest.finanzaspersonales.repository.expense.ExpenseRepository;
import com.apirest.finanzaspersonales.service.expense.ExpenseServiceQuery;
import com.apirest.finanzaspersonales.utils.ExpenseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExpenseServiceQueriesImpl implements ExpenseServiceQuery {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;

    @Override
    public List<ExpenseResponse> getExpensesByUser(Long userId) {
        List<Expense> expenses = expenseRepository.findExpensesByUserId(userId);
        return expenses.stream().map(expenseMapper::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public double getTotalSpentByUser(Long userId) {
        Double totalSpent = expenseRepository.calculateTotalSpentByUser(userId);
        return totalSpent != null ? totalSpent : 0.0;
    }

}
