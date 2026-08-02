package com.restaurantwebsite.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemAdminResponse {
    private Long id;
    private Long posMenuItemId;
    private String nameEn;
    private String nameDe;
    private String descriptionEn;
    private String descriptionDe;
    private String category;
    private String imageUrl;
    private BigDecimal price;
    private boolean active;

    // True if this item has an optional POS link set — no longer verified
    // live against the POS backend, just reflects whether posMenuItemId
    // was ever assigned.
    private boolean linked;
}