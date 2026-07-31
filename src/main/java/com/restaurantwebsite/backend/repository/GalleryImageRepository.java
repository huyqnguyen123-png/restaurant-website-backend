package com.restaurantwebsite.backend.repository;

import com.restaurantwebsite.backend.entity.GalleryCategory;
import com.restaurantwebsite.backend.entity.GalleryImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GalleryImageRepository extends JpaRepository<GalleryImage, Long> {
    List<GalleryImage> findAllByOrderByDisplayOrderAsc();
    List<GalleryImage> findByCategoryOrderByDisplayOrderAsc(GalleryCategory category);
}