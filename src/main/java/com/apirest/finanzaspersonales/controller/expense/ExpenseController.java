package com.apirest.finanzaspersonales.controller.expense;


import com.apirest.finanzaspersonales.controller.expense.model.request.ExpenseRequest;
import com.apirest.finanzaspersonales.controller.expense.model.response.ExpenseResponse;
import com.apirest.finanzaspersonales.service.expense.Impl.ExpenseServiceImpl;
import com.apirest.finanzaspersonales.service.expense.Impl.ExpenseServiceQueriesImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/expenses")

public class ExpenseController {

    private final ExpenseServiceImpl expenseService;
    private final ExpenseServiceQueriesImpl expenseServiceQueries;

    public ExpenseController(ExpenseServiceImpl expenseService, ExpenseServiceQueriesImpl expenseServiceQueries) {
        this.expenseService = expenseService;
        this.expenseServiceQueries = expenseServiceQueries;
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> getExpenses() {
        log.info("Recibiendo solicitud para obtener todas las Expenses");

        List<ExpenseResponse> expenses = expenseService.getExpenses();
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseResponse> getExpenseById(@PathVariable Long expenseId) {
        log.info("Recibiendo solicitud para obtener el Expense con ID: {}", expenseId);

        ExpenseResponse expense = expenseService.getExpenseById(expenseId);
        return ResponseEntity.ok(expense);
    }


    @PostMapping
    public ResponseEntity<ExpenseResponse> createExpense(@Valid @RequestBody ExpenseRequest request) {
        ExpenseResponse response = expenseService.createExpense(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{expenseId}")
    public ResponseEntity<ExpenseResponse> updateExpense(
            @PathVariable Long expenseId,
            @RequestBody @Valid ExpenseRequest expenseRequest) {

        log.info("Recibiendo solicitud para actualizar el Expense con ID: {}", expenseId);

        // Llamamos al servicio para actualizar el Expense y devolver la respuesta
        ExpenseResponse updatedExpense = expenseService.updateExpense(expenseId, expenseRequest);

        return ResponseEntity.ok(updatedExpense);
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<String> deleteExpense(@PathVariable Long expenseId) {
        log.info("Recibiendo solicitud para eliminar el Expense con ID: {}", expenseId);

        // Llamamos al servicio para eliminar el Expense
        expenseService.deleteExpense(expenseId);

        // Retornamos una respuesta exitosa
        return ResponseEntity.ok("Expense con ID: " + expenseId + " eliminada exitosamente.");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ExpenseResponse>> getExpensesByUser(@PathVariable Long userId) {
        List<ExpenseResponse> expenses = expenseServiceQueries.getExpensesByUser(userId);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/total-spent/{userId}")
    public ResponseEntity<Double> getTotalSpentByUser(@PathVariable Long userId) {
        double totalSpent = expenseServiceQueries.getTotalSpentByUser(userId);
        return ResponseEntity.ok(totalSpent);
    }

}
