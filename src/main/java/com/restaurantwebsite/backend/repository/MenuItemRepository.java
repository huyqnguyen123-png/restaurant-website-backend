package com.restaurantwebsite.backend.repository;

import com.restaurantwebsite.backend.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByActiveTrue();
    List<MenuItem> findByActiveTrueAndCategory(String category);
}