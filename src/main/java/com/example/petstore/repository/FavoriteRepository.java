package com.example.petstore.repository;

import com.example.petstore.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUser_Id(Long userId);

    Optional<Favorite> findByUser_IdAndPet_Id(Long userId, Long petId);

    @Query("SELECT f FROM Favorite f JOIN FETCH f.user JOIN FETCH f.pet WHERE f.user.id = :userId AND f.pet.available = true")
    List<Favorite> findByUserIdAndPetAvailable(@Param("userId") Long userId);

    boolean existsByUser_IdAndPet_Id(Long userId, Long petId);
}
