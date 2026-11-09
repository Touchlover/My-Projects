package com.customer_appointment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.customer_appointment.model.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, String> {

    Optional<Customer> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Customer> findByEmailAndPassword(String email, String password);
}
