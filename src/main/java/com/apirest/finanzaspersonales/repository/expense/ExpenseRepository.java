package com.apirest.finanzaspersonales.repository.expense;

import com.apirest.finanzaspersonales.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository <Expense, Long>{

    List<Expense> findByUserId(Long userId); // Obtener gastos por usuario

    // Verificar si la categoría tiene subcategorías activas
    boolean existsByCategoryId(Long categoryId);
}
