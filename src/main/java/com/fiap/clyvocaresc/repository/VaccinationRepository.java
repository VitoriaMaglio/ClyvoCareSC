package com.fiap.clyvocaresc.repository;

import com.fiap.clyvocaresc.entity.Appointment;
import com.fiap.clyvocaresc.entity.Vaccination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccinationRepository extends JpaRepository<Vaccination, Long> {
}