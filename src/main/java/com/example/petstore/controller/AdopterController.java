package com.example.petstore.controller;

import com.example.petstore.dto.ApiResponse;
import com.example.petstore.dto.AdopterDTO;
import com.example.petstore.dto.Mapper;
import com.example.petstore.entity.Adopter;
import com.example.petstore.service.AdopterService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/adopters")
@CrossOrigin
public class AdopterController {

    private final AdopterService adopterService;

    public AdopterController(AdopterService adopterService) {
        this.adopterService = adopterService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody Adopter adopter) {
        Adopter saved = adopterService.create(adopter);
        return ResponseEntity.ok(new ApiResponse(true, "Adopter created successfully", Mapper.toAdopterDTO(saved)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> list() {
        List<AdopterDTO> adopters = adopterService.list()
                .stream().map(Mapper::toAdopterDTO).collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse(true, "Adopter list retrieved", adopters));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Long id) {
        Adopter adopter = adopterService.getById(id);
        return ResponseEntity.ok(new ApiResponse(true, "Adopter found", Mapper.toAdopterDTO(adopter)));
    }

    @PutMapping("/{id}/verify")
    public ResponseEntity<ApiResponse> verify(@PathVariable Long id) {
        Adopter adopter = adopterService.verifyAdopter(id);
        return ResponseEntity.ok(new ApiResponse(true, "Adopter verified successfully", Mapper.toAdopterDTO(adopter)));
    }

    @PutMapping("/{id}/incrementAdoptions")
    public ResponseEntity<ApiResponse> incrementAdoptions(@PathVariable Long id) {
        adopterService.incrementAdoptionCount(id);
        return ResponseEntity.ok(new ApiResponse(true, "Adoption count incremented", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        adopterService.delete(id);
        return ResponseEntity.ok(new ApiResponse(true, "Adopter deleted", null));
    }
}
