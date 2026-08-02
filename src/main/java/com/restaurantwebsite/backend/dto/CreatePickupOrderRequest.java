package com.restaurantwebsite.backend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreatePickupOrderRequest {

    @NotBlank(message = "customerName must not be empty")
    private String customerName;

    @NotBlank(message = "phone must not be empty")
    private String phone;

    @NotNull(message = "pickupTime must not be null")
    private LocalDateTime pickupTime;

    @NotEmpty(message = "items must not be empty")
    private List<@Valid PickupOrderItemRequest> items;
}