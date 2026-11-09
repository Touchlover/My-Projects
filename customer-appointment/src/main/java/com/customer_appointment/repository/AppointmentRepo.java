package com.customer_appointment.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.customer_appointment.model.AppointmentBooking;

@Repository
public interface AppointmentRepo extends JpaRepository<AppointmentBooking, String> {

    long countByStatusIgnoreCase(String status);

    List<AppointmentBooking> findByStatusIgnoreCase(String status);

    List<AppointmentBooking> findByAppointmentDate(LocalDate date);

    Optional<AppointmentBooking> findById(String id);

    List<AppointmentBooking> findByCustomeremailIgnoreCase(String email);

    List<AppointmentBooking> findByPhone(String phone);
}
