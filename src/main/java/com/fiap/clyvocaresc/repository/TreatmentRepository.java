package com.fiap.clyvocaresc.repository;

import com.fiap.clyvocaresc.entity.Appointment;
import com.fiap.clyvocaresc.entity.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
}