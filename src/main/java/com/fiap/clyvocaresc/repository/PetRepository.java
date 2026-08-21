package com.fiap.clyvocaresc.repository;

import com.fiap.clyvocaresc.entity.CatalogItem;
import com.fiap.clyvocaresc.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}