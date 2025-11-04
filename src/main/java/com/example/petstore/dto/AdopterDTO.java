package com.example.petstore.dto;

public class AdopterDTO {
    private Long id;
    private String adopterId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String irsId;
    private boolean verified;
    private int adoptionCount;
    private String createdAt;
    private String updatedAt;

    public AdopterDTO() {}

    public AdopterDTO(Long id, String adopterId, String name, String email, String phone,
                      String address, String irsId, boolean verified, int adoptionCount,
                      String createdAt, String updatedAt) {
        this.id = id;
        this.adopterId = adopterId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.irsId = irsId;
        this.verified = verified;
        this.adoptionCount = adoptionCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}
