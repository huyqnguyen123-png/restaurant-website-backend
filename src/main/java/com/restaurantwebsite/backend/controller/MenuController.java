package com.restaurantwebsite.backend.controller;

import com.restaurantwebsite.backend.dto.*;
import com.restaurantwebsite.backend.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/api/menu")
    public ResponseEntity<List<MenuItemResponse>> getPublicMenu() {
        return ResponseEntity.ok(menuService.getPublicMenu());
    }

    @GetMapping("/api/menu/{id}")
    public ResponseEntity<MenuItemResponse> getPublicMenuItemById(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.getPublicMenuItemById(id));
    }

    @GetMapping("/api/admin/menu")
    public ResponseEntity<List<MenuItemAdminResponse>> getAllForAdmin() {
        return ResponseEntity.ok(menuService.getAllForAdmin());
    }

    @GetMapping("/api/admin/menu/pos-items")
    public ResponseEntity<List<PosMenuItemDto>> getPosMenuItemsForLinking() {
        return ResponseEntity.ok(menuService.getPosMenuItemsForLinking());
    }

    @PostMapping("/api/admin/menu")
    public ResponseEntity<MenuItemAdminResponse> create(@Valid @RequestBody MenuItemRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(menuService.create(request));
    }

    @PutMapping("/api/admin/menu/{id}")
    public ResponseEntity<MenuItemAdminResponse> update(
            @PathVariable Long id, @Valid @RequestBody MenuItemRequest request) {
        return ResponseEntity.ok(menuService.update(id, request));
    }

    @DeleteMapping("/api/admin/menu/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        menuService.delete(id);
        return ResponseEntity.noContent().build();
    }
}