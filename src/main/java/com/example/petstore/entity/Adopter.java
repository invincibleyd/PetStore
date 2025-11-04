package com.example.petstore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
public class Adopter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, updatable = false)
    private String adopterId;  // Unique system ID like A-17302728

    @NotBlank
    private String name;

    @Email
    @Column(unique = true)
    private String email;

    private String phone;
    private String address;

    // ✅ New fields
    @Column(unique = true)
    private String irsId; // Tax/IRS ID (like PAN)

    private boolean verified = false; // Admin approval flag
    private int adoptionCount = 0;    // Number of pets adopted

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Adopter() {}

    public Adopter(String name, String email, String phone, String address, String irsId) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.irsId = irsId;
        this.adopterId = "A-" + System.currentTimeMillis();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ✅ Getters / Setters
    public Long getId() { return id; }

    public String getAdopterId() { return adopterId; }
    public void setAdopterId(String adopterId) { this.adopterId = adopterId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getIrsId() { return irsId; }
    public void setIrsId(String irsId) { this.irsId = irsId; }

    public boolean isVerified() { return verified; }
    public void setVerified(boolean verified) { this.verified = verified; }

    public int getAdoptionCount() { return adoptionCount; }
    public void setAdoptionCount(int adoptionCount) { this.adoptionCount = adoptionCount; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
