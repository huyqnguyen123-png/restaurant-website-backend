package com.restaurantwebsite.backend.dto;

import com.restaurantwebsite.backend.entity.GalleryCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GalleryImageRequest {

    @NotBlank(message = "imageUrl must not be empty")
    private String imageUrl;

    @NotNull(message = "category must not be null")
    private GalleryCategory category;

    private int displayOrder = 0;
}