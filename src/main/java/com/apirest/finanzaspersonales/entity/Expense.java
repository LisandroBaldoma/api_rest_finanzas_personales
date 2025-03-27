package com.apirest.finanzaspersonales.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate date;
    private String description;
    private String paymentMethod;
    private String currency;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // Relación con User

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @JsonIgnore
    private Category category;  // Relación con Category

    public Expense(double amount, LocalDate date, String description, String paymentMethod, String currency, User user, Category category) {
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.paymentMethod = paymentMethod;
        this.currency = currency;
        this.user = user;
        this.category = category;
    }

    public Expense(Long id, double amount, String description, String currency) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.currency = currency;
    }


}
