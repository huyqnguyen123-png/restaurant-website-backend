package com.restaurantwebsite.backend.service;

import com.restaurantwebsite.backend.dto.PosMenuItemDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class PosBackendClient {

    private final RestClient restClient;

    public PosBackendClient(@Value("${pos.backend.url}") String posBackendUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(posBackendUrl)
                .build();
    }

    // Fetches the full menu item list from the POS backend — used both to
    // look up live prices for the public menu, and to populate the admin
    // UI's "link to POS item" dropdown.
    public List<PosMenuItemDto> getAllMenuItems() {
        PosMenuItemDto[] items = restClient.get()
                .uri("/menu-items")
                .retrieve()
                .body(PosMenuItemDto[].class);
        return items != null ? List.of(items) : List.of();
    }
}