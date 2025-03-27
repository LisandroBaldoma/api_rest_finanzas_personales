package com.apirest.finanzaspersonales.controller.expense.model.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseRequest {

    @Positive(message = "El monto debe ser mayor que 0")
    private double amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate date;  // No es obligatorio

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
    private String description;

    private String paymentMethod;  // No es obligatorio
    private String currency;       // No es obligatorio

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long userId;

    @NotNull(message = "El ID de la categoría es obligatorio")
    private Long categoryId;

    public ExpenseRequest(double amount, String description, Long userId, Long categoryId) {
        this.amount = amount;
        this.description = description;
        this.userId = userId;
        this.categoryId = categoryId;
    }
}