package com.restaurantwebsite.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PickupOrderResponse {
    private Long id;
    private String customerName;
    private String phone;
    private LocalDateTime pickupTime;
    private BigDecimal totalAmount;
    private List<PickupOrderItemResponse> items;
    private String notes;
    private LocalDateTime createdAt;
}