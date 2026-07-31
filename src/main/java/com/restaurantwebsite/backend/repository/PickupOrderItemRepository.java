package com.restaurantwebsite.backend.repository;

import com.restaurantwebsite.backend.entity.PickupOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PickupOrderItemRepository extends JpaRepository<PickupOrderItem, Long> {
}