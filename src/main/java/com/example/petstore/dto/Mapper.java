package com.example.petstore.dto;

import com.example.petstore.entity.Adopter;
import com.example.petstore.entity.AdoptionRequest;
import com.example.petstore.entity.Pet;
import com.example.petstore.entity.Owner;
import java.time.format.DateTimeFormatter;

public class Mapper {
    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static AdopterDTO toAdopterDTO(Adopter a) {
        if (a == null) return null;
        return new AdopterDTO(
                a.getId(),
                a.getAdopterId(),
                a.getName(),
                a.getEmail(),
                a.getPhone(),
                a.getAddress(),
                a.getIrsId(),
                a.isVerified(),
                a.getAdoptionCount(),
                a.getCreatedAt() != null ? a.getCreatedAt().format(fmt) : null,
                a.getUpdatedAt() != null ? a.getUpdatedAt().format(fmt) : null
        );
    }

    public static AdoptionRequestDTO toAdoptionRequestDTO(AdoptionRequest r) {
        if (r == null) return null;
        return new AdoptionRequestDTO(
                r.getId(),
                r.getPetId(),
                null, // pet name not available without join
                r.getAdopterId(),
                null, // adopter name not available without join
                r.getStatus().name(),
                r.getCreatedAt().toString()
        );
    }

    public static PetDTO toPetDTO(Pet p) {
        if (p == null) return null;
        return new PetDTO(p.getId(), p.getName(), p.getType(), p.getAge(), p.isAvailable(), p.getOwnerId(), null);
    }
}
