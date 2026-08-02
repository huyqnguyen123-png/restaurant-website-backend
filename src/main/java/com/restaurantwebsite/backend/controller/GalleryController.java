package com.restaurantwebsite.backend.controller;

import com.restaurantwebsite.backend.dto.GalleryImageRequest;
import com.restaurantwebsite.backend.dto.GalleryImageResponse;
import com.restaurantwebsite.backend.entity.GalleryCategory;
import com.restaurantwebsite.backend.service.GalleryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gallery")
@RequiredArgsConstructor
public class GalleryController {

    private final GalleryService galleryService;

    @GetMapping
    public ResponseEntity<List<GalleryImageResponse>> getAll(
            @RequestParam(required = false) GalleryCategory category) {
        return ResponseEntity.ok(galleryService.getAll(category));
    }

    @PostMapping
    public ResponseEntity<GalleryImageResponse> create(@Valid @RequestBody GalleryImageRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(galleryService.create(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        galleryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}