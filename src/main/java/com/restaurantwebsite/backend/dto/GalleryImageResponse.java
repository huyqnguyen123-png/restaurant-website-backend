package com.restaurantwebsite.backend.dto;

import com.restaurantwebsite.backend.entity.GalleryCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GalleryImageResponse {
    private Long id;
    private String imageUrl;
    private GalleryCategory category;
    private int displayOrder;
}