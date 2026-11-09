package com.customer_appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.customer_appointment.model.Branch;

@Repository
public interface BranchRepo extends JpaRepository<Branch, String> {
}
