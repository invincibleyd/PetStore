package com.example.petstore.repository;

import com.example.petstore.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findByActiveTrue();
    List<Pet> findByAvailableTrueAndActiveTrue();
    List<Pet> findByNameContainingIgnoreCaseAndActiveTrue(String name);
    List<Pet> findByTypeIgnoreCaseAndActiveTrue(String type);
    List<Pet> findAllByActiveTrueOrderByAgeAsc();
    List<Pet> findAllByActiveTrueOrderByNameAsc();

    // ✅ Added method for DataInitializer
    Optional<Pet> findByName(String name);
    List<Pet> findByOwnerId(Long ownerId);
}
