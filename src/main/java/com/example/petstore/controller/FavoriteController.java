package com.example.petstore.controller;

import com.example.petstore.dto.ApiResponse;
import com.example.petstore.entity.Favorite;
import com.example.petstore.service.FavoriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@CrossOrigin(origins = "*")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addToFavorites(@RequestBody Favorite favorite) {
        try {
            if (favorite.getUserId() == null) {
                return ResponseEntity.badRequest().body(new ApiResponse(false, "User ID is required", null));
            }
            if (favorite.getPetId() == null) {
                return ResponseEntity.badRequest().body(new ApiResponse(false, "Pet ID is required", null));
            }
            Favorite savedFavorite = favoriteService.addToFavorites(favorite.getUserId(), favorite.getPetId());
            return ResponseEntity.ok(new ApiResponse(true, "Pet added to favorites successfully", savedFavorite));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToFavorites(@RequestParam Long userId, @RequestParam Long petId) {
        try {
            Favorite favorite = favoriteService.addToFavorites(userId, petId);
            return ResponseEntity.ok(new ApiResponse(true, "Pet added to favorites successfully", favorite));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<ApiResponse> removeFromFavorites(@RequestParam Long userId, @RequestParam Long petId) {
        try {
            if (userId == null) {
                return ResponseEntity.badRequest().body(new ApiResponse(false, "User ID is required", null));
            }
            if (petId == null) {
                return ResponseEntity.badRequest().body(new ApiResponse(false, "Pet ID is required", null));
            }
            favoriteService.removeFromFavorites(userId, petId);
            return ResponseEntity.ok(new ApiResponse(true, "Pet removed from favorites successfully", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getUserFavorites(@PathVariable Long userId) {
        try {
            List<Favorite> favorites = favoriteService.getUserFavorites(userId);
            return ResponseEntity.ok(new ApiResponse(true, "Favorites retrieved successfully", favorites));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/user/{userId}/available")
    public ResponseEntity<ApiResponse> getUserAvailableFavorites(@PathVariable Long userId) {
        try {
            List<Favorite> favorites = favoriteService.getUserAvailableFavorites(userId);
            return ResponseEntity.ok(new ApiResponse(true, "Available favorites retrieved successfully", favorites));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/check")
    public ResponseEntity<ApiResponse> isFavorite(@RequestParam Long userId, @RequestParam Long petId) {
        try {
            boolean isFav = favoriteService.isFavorite(userId, petId);
            return ResponseEntity.ok(new ApiResponse(true, "Favorite status checked successfully", isFav));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }
}
