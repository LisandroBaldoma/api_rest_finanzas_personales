package com.apirest.finanzaspersonales.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "parent_category_id"})
}) // Evita duplicados en el mismo padre
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 255)
    private String description;

    private boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> subcategories = new ArrayList<>();

    public Category(Long id, String name, String description, boolean isActive, Category parentCategory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.parentCategory = parentCategory;
    }
}
