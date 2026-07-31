package com.restaurantwebsite.backend.repository;

import com.restaurantwebsite.backend.entity.PickupOrder;
import com.restaurantwebsite.backend.entity.SyncStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PickupOrderRepository extends JpaRepository<PickupOrder, Long> {
    List<PickupOrder> findBySyncStatus(SyncStatus status);
}