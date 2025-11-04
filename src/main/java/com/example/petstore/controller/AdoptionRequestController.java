package com.example.petstore.controller;

import com.example.petstore.dto.ApiResponse;
import com.example.petstore.dto.Mapper;
import com.example.petstore.entity.Adopter;
import com.example.petstore.entity.AdoptionRequest;
import com.example.petstore.service.AdoptionRequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;
import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class AdoptionRequestController {
    private final AdoptionRequestService reqService;

    @Value("${app.admin.key:secret-admin-key}")
    private String adminKey;

    public AdoptionRequestController(AdoptionRequestService reqService) {
        this.reqService = reqService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody AdoptionRequest request) {
        AdoptionRequest r = reqService.create(request.getPetId(), request.getAdopterId(), request.getMessage());
        return ResponseEntity.ok(new ApiResponse(true, "Request created", Mapper.toAdoptionRequestDTO(r)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> list() {
        List<AdoptionRequest> all = reqService.listAll();
        return ResponseEntity.ok(new ApiResponse(true, "Requests",
                all.stream().map(Mapper::toAdoptionRequestDTO).collect(Collectors.toList())));
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<ApiResponse> approve(@PathVariable Long id) {
        AdoptionRequest r = reqService.approve(id);
        return ResponseEntity.ok(new ApiResponse(true, "✅ Request approved", Mapper.toAdoptionRequestDTO(r)));
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<ApiResponse> reject(@PathVariable Long id) {
        AdoptionRequest r = reqService.reject(id);
        return ResponseEntity.ok(new ApiResponse(true, "🚫 Request rejected", Mapper.toAdoptionRequestDTO(r)));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getRequestsByUser(@PathVariable Long userId) {
        List<AdoptionRequest> requests = reqService.getRequestsByUser(userId);
        return ResponseEntity.ok(new ApiResponse(true, "Requests retrieved",
                requests.stream().map(Mapper::toAdoptionRequestDTO).collect(Collectors.toList())));
    }
}
