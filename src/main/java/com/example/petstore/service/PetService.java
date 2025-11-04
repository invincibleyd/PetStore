package com.example.petstore.service;

import com.example.petstore.entity.Pet;
import com.example.petstore.exception.ResourceNotFoundException;
import com.example.petstore.repository.PetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import com.example.petstore.dto.PetDTO;
import com.example.petstore.dto.Mapper;

@Service
public class PetService {
    private static final Logger log = LoggerFactory.getLogger(PetService.class);
    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) { this.petRepository = petRepository; }

    public Pet createPet(Pet pet) { log.info("Creating pet: {}", pet.getName()); return petRepository.save(pet); }
    public Pet updatePet(Long id, Pet updated) { Pet p = petRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pet not found")); p.setName(updated.getName()); p.setType(updated.getType()); p.setAge(updated.getAge()); p.setDescription(updated.getDescription()); p.setImageUrl(updated.getImageUrl()); p.setAvailable(updated.isAvailable()); p.setOwnerId(updated.getOwnerId()); p.setUpdatedAt(updated.getUpdatedAt()); return petRepository.save(p); }
    public List<Pet> getPetsByOwner(Long ownerId) { return petRepository.findByOwnerId(ownerId); }
    public void markAsAdopted(Long petId) { Pet p = petRepository.findById(petId).orElseThrow(() -> new ResourceNotFoundException("Pet not found")); p.setAvailable(false); petRepository.save(p); }
    public List<Pet> listAllActive() { return petRepository.findByActiveTrue(); }
    public List<Pet> listAvailable() { return petRepository.findByAvailableTrueAndActiveTrue(); }
    public Pet getById(Long id) { return petRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pet not found")); }
    public void softDelete(Long id) { Pet p = petRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pet not found")); p.setActive(false); petRepository.save(p); }
    public List<Pet> searchByName(String q) { return petRepository.findByNameContainingIgnoreCaseAndActiveTrue(q); }
    public List<Pet> findByType(String type) { return petRepository.findByTypeIgnoreCaseAndActiveTrue(type); }
    public List<Pet> sortByAgeAsc() { return petRepository.findAllByActiveTrueOrderByAgeAsc(); }
    public List<Pet> sortByNameAsc() { return petRepository.findAllByActiveTrueOrderByNameAsc(); }

    public java.util.List<PetDTO> listAllActiveDTO() { return listAllActive().stream().map(Mapper::toPetDTO).collect(Collectors.toList()); }
    public java.util.List<PetDTO> listAvailableDTO() { return listAvailable().stream().map(Mapper::toPetDTO).collect(Collectors.toList()); }
    public java.util.List<PetDTO> searchByNameDTO(String q) { return searchByName(q).stream().map(Mapper::toPetDTO).collect(Collectors.toList()); }
    public PetDTO getByIdDTO(Long id) { return Mapper.toPetDTO(getById(id)); }
}
