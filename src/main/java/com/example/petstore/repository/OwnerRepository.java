package com.example.petstore.repository;

import com.example.petstore.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    List<Owner> findByActiveTrue();
}
