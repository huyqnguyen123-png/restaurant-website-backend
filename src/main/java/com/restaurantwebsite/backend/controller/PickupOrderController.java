package com.restaurantwebsite.backend.controller;

import com.restaurantwebsite.backend.dto.CreatePickupOrderRequest;
import com.restaurantwebsite.backend.dto.PickupOrderResponse;
import com.restaurantwebsite.backend.service.PickupOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pickup-orders")
@RequiredArgsConstructor
public class PickupOrderController {

    private final PickupOrderService pickupOrderService;

    @PostMapping
    public ResponseEntity<PickupOrderResponse> create(@Valid @RequestBody CreatePickupOrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pickupOrderService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<PickupOrderResponse>> getAll() {
        return ResponseEntity.ok(pickupOrderService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pickupOrderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}