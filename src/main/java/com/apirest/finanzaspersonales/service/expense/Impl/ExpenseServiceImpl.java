package com.apirest.finanzaspersonales.service.expense.Impl;

import com.apirest.finanzaspersonales.entity.Expense;
import com.apirest.finanzaspersonales.repository.expense.ExpenseRepository;
import com.apirest.finanzaspersonales.service.expense.ExpenseService;
import com.apirest.finanzaspersonales.service.expense.ExpenseServiceQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public Expense saveExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    @Override
    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id);
    }

    @Override
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    @Override
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }


}
