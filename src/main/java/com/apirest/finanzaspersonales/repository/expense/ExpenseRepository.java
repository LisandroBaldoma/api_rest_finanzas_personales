package com.apirest.finanzaspersonales.repository.expense;

import com.apirest.finanzaspersonales.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository <Expense, Long>{

    List<Expense> findByUserId(Long userId); // Obtener gastos por usuario

    // Verificar si la categoría tiene subcategorías activas
    boolean existsByCategoryId(Long categoryId);

    //Obtener todos los gastos por usuario.
    @Query("SELECT e FROM Expense e WHERE e.user.id = :userId")
    List<Expense> findExpensesByUserId(@Param("userId") Long userId);

    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user.id = :userId")
    Double calculateTotalSpentByUser(@Param("userId") Long userId);

    //Total gastado por usuario.

    //Total gastado por usuario por categoría.

    //Gastos por categoría (en general).

    //Total gastado por categoría.

    //Gastos entre dos fechas.

    //Gastos agrupados por mes o año.

    //Gastos por método de pago.

    //Gastos con valor máximo.

    //Gastos con valor mínimo.

    //Promedio de gastos por usuario.

    //Gastos por estado de la categoría (activa/inactiva).
}
