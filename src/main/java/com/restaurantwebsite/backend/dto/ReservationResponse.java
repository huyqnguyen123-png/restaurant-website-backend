package com.restaurantwebsite.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse {
    private Long id;
    private String customerName;
    private String phone;
    private LocalDate reservationDate;
    private LocalTime reservationTime;
    private int partySize;
    private String notes;
    private LocalDateTime createdAt;
}