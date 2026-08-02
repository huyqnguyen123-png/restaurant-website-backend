package com.restaurantwebsite.backend.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

// Single source of truth for the restaurant's opening hours — used by both
// ReservationService and PickupOrderService so the two features can never
// drift out of sync with each other. Closed days simply have an empty
// shift list, which naturally makes every time on that day invalid without
// needing a separate "is closed" check anywhere else.
public class BusinessHours {

    public record Shift(LocalTime open, LocalTime close) {
    }

    private static final int RESERVATION_CLOSING_BUFFER_MINUTES = 30;
    private static final int PICKUP_MIN_LEAD_MINUTES = 20;
    private static final int PICKUP_CLOSING_BUFFER_MINUTES = 15;

    private static final Map<DayOfWeek, List<Shift>> SHIFTS = Map.of(
            DayOfWeek.MONDAY, List.of(),
            DayOfWeek.TUESDAY, List.of(
                    new Shift(LocalTime.of(11, 0), LocalTime.of(15, 0)),
                    new Shift(LocalTime.of(17, 0), LocalTime.of(22, 0))),
            DayOfWeek.WEDNESDAY, List.of(
                    new Shift(LocalTime.of(11, 0), LocalTime.of(15, 0)),
                    new Shift(LocalTime.of(17, 0), LocalTime.of(22, 0))),
            DayOfWeek.THURSDAY, List.of(
                    new Shift(LocalTime.of(11, 0), LocalTime.of(15, 0)),
                    new Shift(LocalTime.of(17, 0), LocalTime.of(22, 0))),
            DayOfWeek.FRIDAY, List.of(new Shift(LocalTime.of(11, 0), LocalTime.of(22, 0))),
            DayOfWeek.SATURDAY, List.of(new Shift(LocalTime.of(11, 0), LocalTime.of(22, 0))),
            DayOfWeek.SUNDAY, List.of(new Shift(LocalTime.of(12, 0), LocalTime.of(22, 0)))
    );

    public static List<Shift> shiftsFor(DayOfWeek day) {
        return SHIFTS.getOrDefault(day, List.of());
    }

    // A reservation is valid if it falls within a shift, at least 30
    // minutes before that shift's closing time.
    public static boolean isValidReservationTime(LocalDate date, LocalTime time) {
        for (Shift shift : shiftsFor(date.getDayOfWeek())) {
            LocalTime latestStart = shift.close().minusMinutes(RESERVATION_CLOSING_BUFFER_MINUTES);
            if (!time.isBefore(shift.open()) && !time.isAfter(latestStart)) {
                return true;
            }
        }
        return false;
    }

    // A pickup time is valid if it falls within a shift (no buffer needed —
    // customers are picking food up, not occupying a table).
    public static boolean isValidPickupTime(LocalDateTime dateTime) {
        LocalTime time = dateTime.toLocalTime();
        for (Shift shift : shiftsFor(dateTime.getDayOfWeek())) {
            LocalTime latestPickup = shift.close().minusMinutes(PICKUP_CLOSING_BUFFER_MINUTES);
            if (!time.isBefore(shift.open()) && !time.isAfter(latestPickup)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPickupTimeTooSoon(LocalDateTime dateTime) {
        return dateTime.isBefore(LocalDateTime.now().plusMinutes(PICKUP_MIN_LEAD_MINUTES));
    }
}