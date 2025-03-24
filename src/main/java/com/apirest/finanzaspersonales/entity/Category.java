package com.apirest.finanzaspersonales.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    @JsonBackReference // Evita la recursión infinita al serializar la categoría principal
    private Category parentCategory; // Relación para árbol de categorías

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
    @JsonManagedReference // Gestiona la serialización de las subcategorías
    private List<Category> subcategories; // Lista de subcategorías

}
