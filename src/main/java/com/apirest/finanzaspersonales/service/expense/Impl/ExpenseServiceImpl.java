package com.apirest.finanzaspersonales.service.expense.Impl;

import com.apirest.finanzaspersonales.controller.expense.model.request.ExpenseRequest;
import com.apirest.finanzaspersonales.controller.expense.model.response.ExpenseResponse;
import com.apirest.finanzaspersonales.entity.Category;
import com.apirest.finanzaspersonales.entity.Expense;
import com.apirest.finanzaspersonales.entity.User;
import com.apirest.finanzaspersonales.exceptions.BadRequestException;
import com.apirest.finanzaspersonales.exceptions.InvalidExpenseException;
import com.apirest.finanzaspersonales.exceptions.NotFoundException;
import com.apirest.finanzaspersonales.repository.category.CategoryRepository;
import com.apirest.finanzaspersonales.repository.expense.ExpenseRepository;
import com.apirest.finanzaspersonales.repository.user.UserRepository;
import com.apirest.finanzaspersonales.service.expense.ExpenseService;
import com.apirest.finanzaspersonales.utils.mappers.ExpenseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private static final List<String> VALID_CURRENCIES = List.of("USD", "EUR", "ARS");
    private static final List<String> VALID_PAYMENT_METHODS = List.of("Efectivo", "Débito", "Crédito");

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ExpenseMapper expenseMapper;

    @Override
    public List<ExpenseResponse> getExpenses() {
        log.info("Obteniendo todas las Expenses...");

        List<Expense> expenses = expenseRepository.findAll();
        return expenses.stream()
                .map(expenseMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ExpenseResponse getExpenseById(Long expenseId) {
        log.info("Obteniendo Expense con ID: {}", expenseId);

        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new IllegalArgumentException("Expense no encontrada"));

        return expenseMapper.mapToResponse(expense);
    }

    @Override
    public ExpenseResponse createExpense(ExpenseRequest request) {
        log.info("Creando nueva Expense...");

        // 1️⃣ Asignar valores por defecto si no están en la solicitud
        LocalDate date = (request.getDate() != null) ? request.getDate() : LocalDate.now();
        String currency = (request.getCurrency() != null) ? request.getCurrency() : "ARS";
        String paymentMethod = (request.getPaymentMethod() != null) ? request.getPaymentMethod() : "Efectivo";

        // 2️⃣ Validar valores permitidos
        if (!isValidCurrency(currency)) {
            throw new InvalidExpenseException("Moneda no válida: " + currency + ". Monedas válidas: " + VALID_CURRENCIES);
        }
        if (!isValidPaymentMethod(paymentMethod)) {
            throw new InvalidExpenseException("Método de pago no válido: " + paymentMethod + ". Métodos de pago válidos: " + VALID_PAYMENT_METHODS);
        }

        // 3️⃣ Buscar User y Category en la BD
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada"));

        // 4️⃣ Mapear a Expense y guardar en BD
        Expense expense = expenseMapper.mapToEntity(request, date, currency, paymentMethod, user, category);
        Expense savedExpense = expenseRepository.save(expense);

        log.info("Expense creada con ID: {}", savedExpense.getId());
        return expenseMapper.mapToResponse(savedExpense);
    }

    @Override
    public ExpenseResponse updateExpense(Long expenseId, ExpenseRequest request) {
        log.info("Actualizando Expense con ID: {}", expenseId);

        // Buscar la Expense en la BD
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new NotFoundException("Expense no encontrada"));

        // 1️⃣ No permitir el cambio de usuario
        if (!expense.getUser().getId().equals(request.getUserId())) {
            throw new InvalidExpenseException("No se puede cambiar el usuario de una expense");
        }

        // 2️⃣ Validar categoría (debe existir)
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada"));

        // 3️⃣ Validar moneda y método de pago
        String currency = (request.getCurrency() != null) ? request.getCurrency() : expense.getCurrency();
        if (!isValidCurrency(currency)) {
            throw new InvalidExpenseException("Moneda no válida: " + currency + " Monedas válidas: " + VALID_CURRENCIES);
        }

        String paymentMethod = (request.getPaymentMethod() != null) ? request.getPaymentMethod() : expense.getPaymentMethod();
        if (!isValidPaymentMethod(paymentMethod)) {
            throw new InvalidExpenseException("Método de pago no válido: " + paymentMethod + " Métodos de pago válidos: " + VALID_PAYMENT_METHODS);
        }

        // 4️⃣ Verificar que la fecha no cambie, a menos que tenga sentido
        LocalDate date = (request.getDate() != null) ? request.getDate() : expense.getDate();
        if (date.isBefore(expense.getDate())) {
            throw new BadRequestException("La fecha no puede ser anterior a la fecha original.");
        }

        // 5️⃣ Actualizar los campos de la Expense (con los datos válidos)
        expense.setAmount(request.getAmount());
        expense.setCategory(category);
        expense.setDate(date);
        expense.setCurrency(currency);
        expense.setPaymentMethod(paymentMethod);
        expense.setDescription(request.getDescription());

        // 6️⃣ Guardar los cambios en la BD
        Expense updatedExpense = expenseRepository.save(expense);
        log.info("Expense actualizada con ID: {}", updatedExpense.getId());

        // 7️⃣ Retornar la respuesta con el Expense actualizado
        return expenseMapper.mapToResponse(updatedExpense);
    }

    @Override
    public void deleteExpense(Long expenseId) {
        log.info("Eliminando Expense con ID: {}", expenseId);

        // Verificar si la Expense existe
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new IllegalArgumentException("Expense no encontrada"));

        // Eliminar la Expense
        expenseRepository.delete(expense);

        log.info("Expense con ID: {} eliminada exitosamente", expenseId);
    }

    private boolean isValidCurrency(String currency) {
        return VALID_CURRENCIES.stream()
                .anyMatch(validCurrency -> validCurrency.equalsIgnoreCase(currency));
    }

    private boolean isValidPaymentMethod(String paymentMethod) {
        return VALID_PAYMENT_METHODS.stream()
                .anyMatch(validMethod -> validMethod.equalsIgnoreCase(paymentMethod));
    }
}