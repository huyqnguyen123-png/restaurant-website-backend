package com.restaurantwebsite.backend.service;

import com.restaurantwebsite.backend.dto.GalleryImageRequest;
import com.restaurantwebsite.backend.dto.GalleryImageResponse;
import com.restaurantwebsite.backend.entity.GalleryCategory;
import com.restaurantwebsite.backend.entity.GalleryImage;
import com.restaurantwebsite.backend.exception.ResourceNotFoundException;
import com.restaurantwebsite.backend.repository.GalleryImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GalleryService {

    private final GalleryImageRepository galleryImageRepository;

    public List<GalleryImageResponse> getAll(GalleryCategory category) {
        List<GalleryImage> images = (category != null)
                ? galleryImageRepository.findByCategoryOrderByDisplayOrderAsc(category)
                : galleryImageRepository.findAllByOrderByDisplayOrderAsc();

        return images.stream().map(this::toResponse).toList();
    }

    public GalleryImageResponse create(GalleryImageRequest request) {
        GalleryImage image = new GalleryImage();
        image.setImageUrl(request.getImageUrl());
        image.setCategory(request.getCategory());
        image.setDisplayOrder(request.getDisplayOrder());
        GalleryImage saved = galleryImageRepository.save(image);
        return toResponse(saved);
    }

    public void delete(Long id) {
        GalleryImage image = galleryImageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gallery image not found with id: " + id));
        galleryImageRepository.delete(image);
    }

    private GalleryImageResponse toResponse(GalleryImage image) {
        return new GalleryImageResponse(
                image.getId(),
                image.getImageUrl(),
                image.getCategory(),
                image.getDisplayOrder()
        );
    }
}
