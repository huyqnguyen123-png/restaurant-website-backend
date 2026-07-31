package com.restaurantwebsite.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MenuItemRequest {

    @NotNull(message = "posMenuItemId must not be null")
    private Long posMenuItemId;

    @NotBlank(message = "nameEn must not be empty")
    private String nameEn;

    private String nameDe;
    private String descriptionEn;
    private String descriptionDe;
    private String category;
    private String imageUrl;
    private boolean active = true;
}