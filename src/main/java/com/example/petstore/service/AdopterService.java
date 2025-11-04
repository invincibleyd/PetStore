package com.example.petstore.service;

import com.example.petstore.entity.Adopter;
import com.example.petstore.exception.ResourceNotFoundException;
import com.example.petstore.repository.AdopterRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AdopterService {

    private final AdopterRepository adopterRepository;

    public AdopterService(AdopterRepository adopterRepository) {
        this.adopterRepository = adopterRepository;
    }

    // ✅ Create adopter
    public Adopter create(Adopter adopter) {
        if (adopter.getAdopterId() == null || adopter.getAdopterId().isBlank()) {
            adopter.setAdopterId("A-" + System.currentTimeMillis());
        }
        return adopterRepository.save(adopter);
    }

    // ✅ List all adopters
    public List<Adopter> list() {
        return adopterRepository.findAll();
    }

    // ✅ Get adopter by ID
    public Adopter getById(Long id) {
        return adopterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Adopter not found with id: " + id));
    }

    // ✅ Verify adopter (mark verified = true)
    public Adopter verifyAdopter(Long id) {
        Adopter adopter = getById(id);
        adopter.setVerified(true);
        return adopterRepository.save(adopter);
    }

    // ✅ Delete adopter
    public void delete(Long id) {
        Adopter adopter = getById(id);
        adopterRepository.delete(adopter);
    }

    // ✅ Increment adoption count
    public void incrementAdoptionCount(Long id) {
        Adopter adopter = getById(id);
        adopter.setAdoptionCount(adopter.getAdoptionCount() + 1);
        adopterRepository.save(adopter);
    }

    // ✅ Find by email or adopterId
    public Optional<Adopter> findByEmail(String email) {
        return adopterRepository.findByEmail(email);
    }

    public Optional<Adopter> findByAdopterId(String adopterId) {
        return adopterRepository.findByAdopterId(adopterId);
    }
}
