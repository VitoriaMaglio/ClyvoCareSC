package com.fiap.clyvocaresc.repository;

import com.fiap.clyvocaresc.entity.Appointment;
import com.fiap.clyvocaresc.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long> {
}