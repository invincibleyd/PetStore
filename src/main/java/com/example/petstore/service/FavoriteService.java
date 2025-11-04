package com.example.petstore.service;

import com.example.petstore.entity.Adopter;
import com.example.petstore.entity.Favorite;
import com.example.petstore.entity.Pet;
import com.example.petstore.entity.User;
import com.example.petstore.repository.AdopterRepository;
import com.example.petstore.repository.FavoriteRepository;
import com.example.petstore.repository.PetRepository;
import com.example.petstore.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final PetRepository petRepository;

    public FavoriteService(FavoriteRepository favoriteRepository,
                          UserRepository userRepository,
                          PetRepository petRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.petRepository = petRepository;
    }

    public Favorite addToFavorites(Long userId, Long petId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        if (!pet.isAvailable()) {
            throw new RuntimeException("Pet is not available for adoption");
        }

        if (favoriteRepository.existsByUser_IdAndPet_Id(userId, petId)) {
            throw new RuntimeException("Pet is already in favorites");
        }

        Favorite favorite = new Favorite(user, pet);
        return favoriteRepository.save(favorite);
    }

    public void removeFromFavorites(Long userId, Long petId) {
        Favorite favorite = favoriteRepository.findByUser_IdAndPet_Id(userId, petId)
                .orElseThrow(() -> new RuntimeException("Favorite not found"));
        favoriteRepository.delete(favorite);
    }

    public List<Favorite> getUserFavorites(Long userId) {
        return favoriteRepository.findByUser_Id(userId);
    }

    public List<Favorite> getUserAvailableFavorites(Long userId) {
        return favoriteRepository.findByUserIdAndPetAvailable(userId);
    }

    public boolean isFavorite(Long userId, Long petId) {
        return favoriteRepository.existsByUser_IdAndPet_Id(userId, petId);
    }
}
