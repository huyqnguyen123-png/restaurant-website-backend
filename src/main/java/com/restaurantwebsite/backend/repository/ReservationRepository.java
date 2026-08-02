package com.restaurantwebsite.backend.repository;

import com.restaurantwebsite.backend.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByOrderByReservationDateAscReservationTimeAsc();
}