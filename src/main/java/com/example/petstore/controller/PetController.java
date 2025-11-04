package com.example.petstore.controller;

import com.example.petstore.dto.ApiResponse;
import com.example.petstore.entity.Pet;
import com.example.petstore.dto.Mapper;
import com.example.petstore.service.PetService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pets")
public class PetController {
    private final PetService petService;
    public PetController(PetService petService) { this.petService = petService; }

    @PostMapping
    public ResponseEntity<ApiResponse> createPet(@Valid @RequestBody Pet pet) {
        var saved = petService.createPet(pet);
        return ResponseEntity.ok(new ApiResponse(true, "Pet created", Mapper.toPetDTO(saved)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> list(@RequestParam(required = false) String q,
                                          @RequestParam(required = false) String type,
                                          @RequestParam(required = false) Boolean available,
                                          @RequestParam(required = false) String sort) {
        if (q != null && !q.isBlank()) return ResponseEntity.ok(new ApiResponse(true, "Search results", petService.searchByNameDTO(q)));
        if (type != null && !type.isBlank()) return ResponseEntity.ok(new ApiResponse(true, "Filter by type", petService.findByType(type).stream().map(Mapper::toPetDTO).collect(Collectors.toList())));
        if (available != null && available) return ResponseEntity.ok(new ApiResponse(true, "Available pets", petService.listAvailableDTO()));
        if ("age".equalsIgnoreCase(sort)) return ResponseEntity.ok(new ApiResponse(true, "Sorted by age", petService.sortByAgeAsc().stream().map(Mapper::toPetDTO).collect(Collectors.toList())));
        if ("name".equalsIgnoreCase(sort)) return ResponseEntity.ok(new ApiResponse(true, "Sorted by name", petService.sortByNameAsc().stream().map(Mapper::toPetDTO).collect(Collectors.toList())));
        return ResponseEntity.ok(new ApiResponse(true, "All pets", petService.listAllActiveDTO()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse(true, "Pet fetched", petService.getByIdDTO(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @Valid @RequestBody Pet pet) {
        var updated = petService.updatePet(id, pet);
        return ResponseEntity.ok(new ApiResponse(true, "Pet updated", Mapper.toPetDTO(updated)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        petService.softDelete(id);
        return ResponseEntity.ok(new ApiResponse(true, "Pet soft-deleted", null));
    }
}
