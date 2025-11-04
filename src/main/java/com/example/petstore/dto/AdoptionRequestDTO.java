package com.example.petstore.dto;

import java.time.Instant;

public class AdoptionRequestDTO {
    private Long id;
    private Long petId;
    private String petName;
    private Long adopterId;
    private String adopterName;
    private String status;
    private String createdAt;

    public AdoptionRequestDTO() {}

    public AdoptionRequestDTO(Long id, Long petId, String petName, Long adopterId, String adopterName, String status, String createdAt) {
        this.id = id; this.petId = petId; this.petName = petName; this.adopterId = adopterId; this.adopterName = adopterName; this.status = status; this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPetId() { return petId; }
    public void setPetId(Long petId) { this.petId = petId; }
    public String getPetName() { return petName; }
    public void setPetName(String petName) { this.petName = petName; }
    public Long getAdopterId() { return adopterId; }
    public void setAdopterId(Long adopterId) { this.adopterId = adopterId; }
    public String getAdopterName() { return adopterName; }
    public void setAdopterName(String adopterName) { this.adopterName = adopterName; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    
}
