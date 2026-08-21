package com.fiap.clyvocaresc.repository;

import com.fiap.clyvocaresc.entity.Appointment;
import com.fiap.clyvocaresc.entity.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
}