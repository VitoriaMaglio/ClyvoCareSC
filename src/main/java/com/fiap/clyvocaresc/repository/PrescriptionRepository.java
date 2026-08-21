package com.fiap.clyvocaresc.repository;

import com.fiap.clyvocaresc.entity.Appointment;
import com.fiap.clyvocaresc.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
}