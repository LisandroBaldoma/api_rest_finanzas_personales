package com.apirest.finanzaspersonales.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "expenses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate date;
    private String description;
    private String paymentMethod;
    private String currency;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // Relación con User

    //@ManyToOne
    //@JoinColumn(name = "category_id", nullable = false)
    //private Category category;  // Relación con Category
}
