package com.restaurantwebsite.backend.repository;

import com.restaurantwebsite.backend.entity.Reservation;
import com.restaurantwebsite.backend.entity.SyncStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findBySyncStatus(SyncStatus status);
}