package com.fiap.clyvocaresc.repository;

import com.fiap.clyvocaresc.entity.CatalogItem;
import com.fiap.clyvocaresc.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}