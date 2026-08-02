package com.restaurantwebsite.backend.repository;

import com.restaurantwebsite.backend.entity.PickupOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PickupOrderRepository extends JpaRepository<PickupOrder, Long> {
}