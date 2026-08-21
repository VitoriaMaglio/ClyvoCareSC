package com.fiap.clyvocaresc.repository;

import com.fiap.clyvocaresc.entity.CatalogItem;
import com.fiap.clyvocaresc.entity.Veterinarian;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeterinarianRepository extends JpaRepository<Veterinarian, Long> {
}