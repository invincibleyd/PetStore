package com.example.petstore.dto;

public class PetDTO {
    private Long id;
    private String name;
    private String type;
    private Integer age;
    private boolean available;
    private Long ownerId;
    private String ownerName;

    public PetDTO() {}
    public PetDTO(Long id, String name, String type, Integer age, boolean available, Long ownerId, String ownerName) { this.id = id; this.name = name; this.type = type; this.age = age; this.available = available; this.ownerId = ownerId; this.ownerName = ownerName; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }
    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
}
