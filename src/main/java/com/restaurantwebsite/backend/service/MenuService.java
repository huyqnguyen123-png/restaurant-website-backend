package com.restaurantwebsite.backend.service;

import com.restaurantwebsite.backend.dto.*;
import com.restaurantwebsite.backend.entity.MenuItem;
import com.restaurantwebsite.backend.exception.ResourceNotFoundException;
import com.restaurantwebsite.backend.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuItemRepository menuItemRepository;
    private final PosBackendClient posBackendClient;

    // Public-facing menu: only active items, each enriched with its current
    // price fetched live from pos-backend. Items whose linked POS item no
    // longer exists are silently excluded — showing a dish with no price
    // would be confusing for customers.
    public List<MenuItemResponse> getPublicMenu() {
        Map<Long, BigDecimal> priceByPosId = posBackendClient.getAllMenuItems().stream()
                .collect(Collectors.toMap(PosMenuItemDto::getId, PosMenuItemDto::getPrice));

        return menuItemRepository.findByActiveTrue().stream()
                .filter(item -> priceByPosId.containsKey(item.getPosMenuItemId()))
                .map(item -> new MenuItemResponse(
                        item.getId(),
                        item.getNameEn(),
                        item.getNameDe(),
                        item.getDescriptionEn(),
                        item.getDescriptionDe(),
                        item.getCategory(),
                        item.getImageUrl(),
                        priceByPosId.get(item.getPosMenuItemId())
                ))
                .toList();
    }

    // Admin view: every item regardless of active status, flagging whether
    // its POS link is still valid so staff can spot and fix broken mappings.
    public List<MenuItemAdminResponse> getAllForAdmin() {
        Map<Long, BigDecimal> priceByPosId = posBackendClient.getAllMenuItems().stream()
                .collect(Collectors.toMap(PosMenuItemDto::getId, PosMenuItemDto::getPrice));

        return menuItemRepository.findAll().stream()
                .map(item -> new MenuItemAdminResponse(
                        item.getId(),
                        item.getPosMenuItemId(),
                        item.getNameEn(),
                        item.getNameDe(),
                        item.getDescriptionEn(),
                        item.getDescriptionDe(),
                        item.getCategory(),
                        item.getImageUrl(),
                        item.isActive(),
                        priceByPosId.containsKey(item.getPosMenuItemId())
                ))
                .toList();
    }

    public MenuItemAdminResponse create(MenuItemRequest request) {
        MenuItem item = new MenuItem();
        applyRequest(item, request);
        MenuItem saved = menuItemRepository.save(item);
        return toAdminResponse(saved, true);
    }

    public MenuItemAdminResponse update(Long id, MenuItemRequest request) {
        MenuItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + id));
        applyRequest(item, request);
        MenuItem saved = menuItemRepository.save(item);
        return toAdminResponse(saved, true);
    }

    public void delete(Long id) {
        MenuItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + id));
        menuItemRepository.delete(item);
    }

    // Used by the admin UI to populate the "link to POS item" dropdown.
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
        item.setActive(request.isActive());
    }

    private MenuItemAdminResponse toAdminResponse(MenuItem item, boolean linkValid) {
        return new MenuItemAdminResponse(
                item.getId(),
                item.getPosMenuItemId(),
                item.getNameEn(),
                item.getNameDe(),
                item.getDescriptionEn(),
                item.getDescriptionDe(),
                item.getCategory(),
                item.getImageUrl(),
                item.isActive(),
                linkValid
        );
    }
}