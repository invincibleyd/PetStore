package com.example.petstore.controller;

import com.example.petstore.dto.ApiResponse;
import com.example.petstore.dto.Mapper;
import com.example.petstore.entity.Owner;
import com.example.petstore.repository.OwnerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/owners")
@CrossOrigin(origins = "*")
public class OwnerController {

    private final OwnerRepository ownerRepository;

    public OwnerController(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerOwner(@Valid @RequestBody Owner owner) {
        try {
            Owner savedOwner = ownerRepository.save(owner);
            return ResponseEntity.ok(new ApiResponse(true, "Owner registered successfully", savedOwner));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Registration failed: " + e.getMessage(), null));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllOwners() {
        try {
            List<Owner> owners = ownerRepository.findByActiveTrue();
            return ResponseEntity.ok(new ApiResponse(true, "Owners retrieved successfully", owners));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to retrieve owners: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getOwnerById(@PathVariable Long id) {
        try {
            Owner owner = ownerRepository.findById(id).orElse(null);
            if (owner == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(new ApiResponse(true, "Owner retrieved successfully", owner));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to retrieve owner: " + e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateOwner(@PathVariable Long id, @Valid @RequestBody Owner ownerDetails) {
        try {
            Owner owner = ownerRepository.findById(id).orElse(null);
            if (owner == null) {
                return ResponseEntity.notFound().build();
            }
            owner.setName(ownerDetails.getName());
            owner.setActive(ownerDetails.isActive());
            Owner updatedOwner = ownerRepository.save(owner);
            return ResponseEntity.ok(new ApiResponse(true, "Owner updated successfully", updatedOwner));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to update owner: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteOwner(@PathVariable Long id) {
        try {
            Owner owner = ownerRepository.findById(id).orElse(null);
            if (owner == null) {
                return ResponseEntity.notFound().build();
            }
            owner.setActive(false); // Soft delete
            ownerRepository.save(owner);
            return ResponseEntity.ok(new ApiResponse(true, "Owner deactivated successfully", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to delete owner: " + e.getMessage(), null));
        }
    }
}
