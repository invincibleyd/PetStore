package com.example.petstore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Owner name is required")
    private String name;

    private boolean active = true;

    @OneToMany(mappedBy = "ownerId", cascade = CascadeType.ALL)
    private List<Pet> pets;

    public Owner() {}
    public Owner(String name) { this.name = name; this.active = true; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public List<Pet> getPets() { return pets; }
    public void setPets(List<Pet> pets) { this.pets = pets; }
}
