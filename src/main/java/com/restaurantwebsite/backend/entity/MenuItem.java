package com.restaurantwebsite.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// The website's own, richer menu content (full names, descriptions, images,
// categories for filtering) — separate from the POS app's short internal
// names. posMenuItemId links each row to the corresponding MenuItem.id in
// the pos-backend database. This is NOT a real foreign key (the two
// databases are entirely separate) — it's just a plain reference number
// used when building an order to send to the POS backend, and price is
// always fetched live from the POS backend rather than duplicated here,
// so there's only one source of truth for pricing.
@Entity
@Table(name = "menu_items")
@Data
@NoArgsConstructor
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long posMenuItemId;

    @Column(nullable = false)
    private String nameEn;

    // Nullable: falls back to nameEn on the frontend if not filled in.
    private String nameDe;

    @Column(length = 1000)
    private String descriptionEn;

    @Column(length = 1000)
    private String descriptionDe;

    // Free-text category (e.g. "Nigiri", "Sashimi", "Maki") rather than a
    // fixed enum, so new categories can be added later without a code change.
    private String category;

    private String imageUrl;

    @Column(nullable = false)
    private boolean active = true;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}