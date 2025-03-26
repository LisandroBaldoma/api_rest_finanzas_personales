package com.apirest.finanzaspersonales.controller.expense.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ExpenseResponse {

    @Positive(message = "El monto debe ser mayor que 0")
    private double amount;

    @NotNull(message = "La fecha no puede ser nula")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate date;

    @NotBlank(message = "La descripción es obligatoria")
    private String description;

    @NotBlank(message = "El método de pago es obligatorio")
    private String paymentMethod;

    @NotBlank(message = "La moneda es obligatoria")
    private String currency;

    @NotBlank(message = "El nombre del usuario no puede estar vacío")
    private String userName;

    @NotBlank(message = "El nombre de la categoría no puede estar vacío")
    private String categoryName;
}
