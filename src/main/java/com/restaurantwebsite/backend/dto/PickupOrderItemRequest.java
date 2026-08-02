package com.restaurantwebsite.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PickupOrderItemRequest {

    @NotNull(message = "menuItemId must not be null")
    private Long menuItemId;

    @Min(value = 1, message = "quantity must be at least 1")
    private int quantity;
}