package com.example.petstore.repository;

import com.example.petstore.entity.Adopter;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AdopterRepository extends JpaRepository<Adopter, Long> {
    Optional<Adopter> findByEmail(String email);
    Optional<Adopter> findByAdopterId(String adopterId);
}
