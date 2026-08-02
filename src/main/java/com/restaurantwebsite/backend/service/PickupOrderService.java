package com.restaurantwebsite.backend.service;

import com.restaurantwebsite.backend.dto.*;
import com.restaurantwebsite.backend.entity.MenuItem;
import com.restaurantwebsite.backend.entity.PickupOrder;
import com.restaurantwebsite.backend.entity.PickupOrderItem;
import com.restaurantwebsite.backend.exception.ResourceNotFoundException;
import com.restaurantwebsite.backend.repository.MenuItemRepository;
import com.restaurantwebsite.backend.repository.PickupOrderRepository;
import com.restaurantwebsite.backend.util.BusinessHours;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PickupOrderService {

    private final PickupOrderRepository pickupOrderRepository;
    private final MenuItemRepository menuItemRepository;
    private final PosBackendClient posBackendClient;

    public PickupOrderResponse create(CreatePickupOrderRequest request) {
        if (BusinessHours.isPickupTimeTooSoon(request.getPickupTime())) {
            throw new IllegalArgumentException("Pickup time must be at least 20 minutes from now.");
        }
        if (!BusinessHours.isValidPickupTime(request.getPickupTime())) {
            throw new IllegalArgumentException(
                    "Selected pickup time is outside our opening hours. Please choose a valid time.");
        }

        PickupOrder order = new PickupOrder();
        order.setCustomerName(request.getCustomerName());
        order.setPhone(request.getPhone());
        order.setPickupTime(request.getPickupTime());

        BigDecimal total = BigDecimal.ZERO;

        for (PickupOrderItemRequest itemRequest : request.getItems()) {
            MenuItem menuItem = menuItemRepository.findById(itemRequest.getMenuItemId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Menu item not found with id: " + itemRequest.getMenuItemId()));

            PickupOrderItem orderItem = new PickupOrderItem();
            orderItem.setPickupOrder(order);
            orderItem.setMenuItem(menuItem);
            orderItem.setItemNameSnapshot(menuItem.getNameEn());
            orderItem.setUnitPriceSnapshot(menuItem.getPrice());
            orderItem.setQuantity(itemRequest.getQuantity());

            order.getItems().add(orderItem);
            total = total.add(menuItem.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity())));
        }

        order.setTotalAmount(total);

        PickupOrder saved = pickupOrderRepository.save(order);

        // Push to the POS app so staff see it immediately, using each item's
        // exact website name/price as a custom line item — never blocks or
        // fails the customer's order if the POS backend is unreachable.
        pushToPos(saved);

        return toResponse(saved);
    }

    private void pushToPos(PickupOrder order) {
        try {
            Long posOrderId = posBackendClient.createOrder(
                    new PosCreateOrderRequest("TAKEAWAY", order.getCustomerName()));

            for (PickupOrderItem item : order.getItems()) {
                posBackendClient.addOrderItem(posOrderId, new PosAddOrderItemRequest(
                        item.getItemNameSnapshot(),
                        item.getUnitPriceSnapshot(),
                        item.getQuantity()
                ));
            }
        } catch (Exception e) {
            log.warn("Failed to push pickup order {} to POS backend: {}", order.getId(), e.getMessage());
        }
    }

    public List<PickupOrderResponse> getAll() {
        return pickupOrderRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public void delete(Long id) {
        PickupOrder order = pickupOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pickup order not found with id: " + id));
        pickupOrderRepository.delete(order);
    }

    private PickupOrderResponse toResponse(PickupOrder order) {
        List<PickupOrderItemResponse> items = order.getItems().stream()
                .map(i -> new PickupOrderItemResponse(i.getItemNameSnapshot(), i.getUnitPriceSnapshot(), i.getQuantity()))
                .toList();

        return new PickupOrderResponse(
                order.getId(),
                order.getCustomerName(),
                order.getPhone(),
                order.getPickupTime(),
                order.getTotalAmount(),
                items,
                order.getCreatedAt()
        );
    }
}