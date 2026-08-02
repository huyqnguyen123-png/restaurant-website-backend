package com.restaurantwebsite.backend.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MenuItemRequest {

    // Optional now — only set this if you want to manually link this item
    // to a specific menu item in the POS app for reference purposes.
    private Long posMenuItemId;

    @NotBlank(message = "nameEn must not be empty")
    private String nameEn;

    private String nameDe;
    private String descriptionEn;
    private String descriptionDe;
    private String category;
    private String imageUrl;

    @NotNull(message = "price must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "price must be greater than 0")
    private BigDecimal price;

    private boolean active = true;
}