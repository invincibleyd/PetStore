package com.example.petstore.repository;

import com.example.petstore.entity.AdoptionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AdoptionRequestRepository extends JpaRepository<AdoptionRequest, Long> {
    List<AdoptionRequest> findAllByOrderByCreatedAtDesc();
    List<AdoptionRequest> findByStatus(AdoptionRequest.Status status);
    List<AdoptionRequest> findByAdopterId(Long adopterId);
    List<AdoptionRequest> findByPetId(Long petId);
}
