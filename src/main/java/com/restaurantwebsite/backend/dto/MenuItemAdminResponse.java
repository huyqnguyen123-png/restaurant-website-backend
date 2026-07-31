package com.restaurantwebsite.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemAdminResponse {
    private Long id;
    private Long posMenuItemId;
    private String nameEn;
    private String nameDe;
    private String descriptionEn;
    private String descriptionDe;
    private String category;
    private String imageUrl;
    private boolean active;

    // True if posMenuItemId currently matches a real item in pos-backend —
    // lets the admin screen flag broken links (e.g. after staff deletes the
    // linked item on the POS side).
    private boolean linkValid;
}