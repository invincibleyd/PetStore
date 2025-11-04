package com.example.petstore.service;
import java.time.Instant;  // add import

import com.example.petstore.entity.AdoptionRequest;
import com.example.petstore.entity.Adopter;
import com.example.petstore.entity.Pet;
import com.example.petstore.exception.ResourceNotFoundException;
import com.example.petstore.repository.AdoptionRequestRepository;
import com.example.petstore.repository.PetRepository;
import com.example.petstore.repository.AdopterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdoptionRequestService {
    private static final Logger log = LoggerFactory.getLogger(AdoptionRequestService.class);
    private final AdoptionRequestRepository reqRepo;
    private final PetRepository petRepo;
    private final AdopterRepository adopterRepo;

    public AdoptionRequestService(AdoptionRequestRepository reqRepo, PetRepository petRepo, AdopterRepository adopterRepo) {
        this.reqRepo = reqRepo;
        this.petRepo = petRepo;
        this.adopterRepo = adopterRepo;
    }

    public AdoptionRequest create(Long petId, Long adopterId, String message) {
        Pet pet = petRepo.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));
        if (!pet.isAvailable())
            throw new IllegalStateException("Pet not available for adoption");

        AdoptionRequest r = new AdoptionRequest();
        r.setPetId(petId);
        r.setAdopterId(adopterId);
        r.setMessage(message);
        return reqRepo.save(r);
    }

    public List<AdoptionRequest> getRequestsByUser(Long userId) {
        return reqRepo.findByAdopterId(userId);
    }

    public List<AdoptionRequest> getRequestsByPet(Long petId) {
        return reqRepo.findByPetId(petId);
    }

    public List<AdoptionRequest> listAll() {
        return reqRepo.findAll();
    }

    public List<AdoptionRequest> listByStatus(AdoptionRequest.Status status) {
        return reqRepo.findByStatus(status);
    }

    public AdoptionRequest approve(Long requestId) {
        AdoptionRequest r = reqRepo.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));
        if (r.getStatus() != AdoptionRequest.Status.PENDING)
            throw new IllegalStateException("Request not pending");

        Pet pet = petRepo.findById(r.getPetId())
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));
        pet.setAvailable(false);
        petRepo.save(pet);

        r.setStatus(AdoptionRequest.Status.APPROVED);
        r.setAdminNotes("Approved by admin");
        r.setUpdatedAt(java.time.LocalDateTime.now());
        return reqRepo.save(r);
    }

    public AdoptionRequest reject(Long requestId) {
        AdoptionRequest r = reqRepo.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));
        if (r.getStatus() != AdoptionRequest.Status.PENDING)
            throw new IllegalStateException("Request not pending");

        r.setStatus(AdoptionRequest.Status.REJECTED);
        r.setAdminNotes("Rejected by admin");
        r.setUpdatedAt(java.time.LocalDateTime.now());
        return reqRepo.save(r);
    }
}
