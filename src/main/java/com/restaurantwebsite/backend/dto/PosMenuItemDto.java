package com.restaurantwebsite.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

// Mirrors the relevant fields of pos-backend's MenuItemResponse. Extra
// fields in the real JSON response (like createdAt) are simply ignored by
// Jackson during deserialization — no need to declare every field here.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PosMenuItemDto {
    private Long id;
    private String name;
    private BigDecimal price;
}