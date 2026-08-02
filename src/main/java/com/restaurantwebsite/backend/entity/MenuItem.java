package com.restaurantwebsite.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// The website's own, fully independent menu content — name, description,
// price and category are all stored here directly, with no dependency on
// the POS backend. posMenuItemId is kept as an OPTIONAL reference in case
// staff later want to manually link a website item to a specific POS item
// for reporting purposes, but nothing in ordering or display requires it.
@Entity
@Table(name = "menu_items")
@Data
@NoArgsConstructor
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Optional — no longer required for the item to function.
    private Long posMenuItemId;

    @Column(nullable = false)
    private String nameEn;

    private String nameDe;

    @Column(length = 1000)
    private String descriptionEn;

    @Column(length = 1000)
    private String descriptionDe;

    private String category;

    private String imageUrl;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private boolean active = true;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}