package com.example.petstore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull(message = "Pet is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    private LocalDateTime createdAt;

    // Additional fields for JSON deserialization
    @Transient
    private Long userId;

    @Transient
    private Long petId;

    public Favorite() {
        this.createdAt = LocalDateTime.now();
    }

    public Favorite(User user, Pet pet) {
        this.user = user;
        this.pet = pet;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Long getUserId() { return user != null ? user.getId() : userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Pet getPet() { return pet; }
    public void setPet(Pet pet) { this.pet = pet; }

    public Long getPetId() { return pet != null ? pet.getId() : petId; }
    public void setPetId(Long petId) { this.petId = petId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
