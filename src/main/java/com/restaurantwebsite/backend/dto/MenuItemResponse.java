package com.restaurantwebsite.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

// Public-facing shape returned by GET /api/menu — price is always fetched
// live from pos-backend at request time, never stored locally.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemResponse {
    private Long id;
    private String nameEn;
    private String nameDe;
    private String descriptionEn;
    private String descriptionDe;
    private String category;
    private String imageUrl;
    private BigDecimal price;
}