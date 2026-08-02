package com.restaurantwebsite.backend.service;

import com.restaurantwebsite.backend.dto.*;
import com.restaurantwebsite.backend.entity.MenuItem;
import com.restaurantwebsite.backend.exception.ResourceNotFoundException;
import com.restaurantwebsite.backend.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuItemRepository menuItemRepository;
    private final PosBackendClient posBackendClient;

    // Fully independent — reads only from the website's own database, no
    // network call to pos-backend required.
    public List<MenuItemResponse> getPublicMenu() {
        return menuItemRepository.findByActiveTrue().stream()
                .map(this::toResponse)
                .toList();
    }

    public MenuItemResponse getPublicMenuItemById(Long id) {
        MenuItem item = menuItemRepository.findById(id)
                .filter(MenuItem::isActive)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + id));
        return toResponse(item);
    }

    public List<MenuItemAdminResponse> getAllForAdmin() {
        return menuItemRepository.findAll().stream()
                .map(this::toAdminResponse)
                .toList();
    }

    public MenuItemAdminResponse create(MenuItemRequest request) {
        MenuItem item = new MenuItem();
        applyRequest(item, request);
        MenuItem saved = menuItemRepository.save(item);
        return toAdminResponse(saved);
    }

    public MenuItemAdminResponse update(Long id, MenuItemRequest request) {
        MenuItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + id));
        applyRequest(item, request);
        MenuItem saved = menuItemRepository.save(item);
        return toAdminResponse(saved);
    }

    public void delete(Long id) {
        MenuItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + id));
        menuItemRepository.delete(item);
    }

    // Still available for the admin UI's optional "link to a POS item"
    // dropdown, but only called when the admin explicitly opens that
    // feature — never on every menu page load.
    public List<PosMenuItemDto> getPosMenuItemsForLinking() {
        return posBackendClient.getAllMenuItems();
    }

    private void applyRequest(MenuItem item, MenuItemRequest request) {
        item.setPosMenuItemId(request.getPosMenuItemId());
        item.setNameEn(request.getNameEn());
        item.setNameDe(request.getNameDe());
        item.setDescriptionEn(request.getDescriptionEn());
        item.setDescriptionDe(request.getDescriptionDe());
        item.setCategory(request.getCategory());
        item.setImageUrl(request.getImageUrl());
        item.setPrice(request.getPrice());
        item.setActive(request.isActive());
    }

    private MenuItemResponse toResponse(MenuItem item) {
        return new MenuItemResponse(
                item.getId(),
                item.getNameEn(),
                item.getNameDe(),
                item.getDescriptionEn(),
                item.getDescriptionDe(),
                item.getCategory(),
                item.getImageUrl(),
                item.getPrice()
        );
    }

    private MenuItemAdminResponse toAdminResponse(MenuItem item) {
        return new MenuItemAdminResponse(
                item.getId(),
                item.getPosMenuItemId(),
                item.getNameEn(),
                item.getNameDe(),
                item.getDescriptionEn(),
                item.getDescriptionDe(),
                item.getCategory(),
                item.getImageUrl(),
                item.getPrice(),
                item.isActive(),
                item.getPosMenuItemId() != null
        );
    }
}