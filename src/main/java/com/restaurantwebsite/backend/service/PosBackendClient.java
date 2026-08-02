package com.restaurantwebsite.backend.service;

import com.restaurantwebsite.backend.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PosBackendClient {

    private final RestClient restClient;

    public PosBackendClient(@Value("${pos.backend.url}") String posBackendUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(posBackendUrl)
                .build();
    }

    public List<PosMenuItemDto> getAllMenuItems() {
        PosMenuItemDto[] items = restClient.get()
                .uri("/menu-items")
                .retrieve()
                .body(PosMenuItemDto[].class);
        return items != null ? List.of(items) : List.of();
    }

    // Creates the order header in pos-backend and returns its id, so the
    // caller can then attach each line item to it individually.
    public Long createOrder(PosCreateOrderRequest request) {
        Map<?, ?> response = restClient.post()
                .uri("/orders")
                .body(request)
                .retrieve()
                .body(Map.class);
        return Long.valueOf(response.get("id").toString());
    }

    public void addOrderItem(Long orderId, PosAddOrderItemRequest request) {
        restClient.post()
                .uri("/orders/{orderId}/items", orderId)
                .body(request)
                .retrieve()
                .toBodilessEntity();
    }

    public void createReservation(PosCreateReservationRequest request) {
        restClient.post()
                .uri("/reservations")
                .body(request)
                .retrieve()
                .toBodilessEntity();
    }
}