package com.customer_appointment.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "appointments")
public class AppointmentBooking {

    @Id
    private String id;
    private String branch_id;
    private String branchName;
    private String staff_id;
    private String customerName;
    private String customeremail;
    private String phone;
    private String address;
    private String accountNumber;
    private String serviceType; // Loan Consultation, Account Opening, Financial Planning, General Banking, etc.

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate appointmentDate;

    private String appointmentTime;
    private String appointmentDuration;
    private String status; // 'booked', 'confirmed', 'pending', 'cancelled', 'completed'

    @Column(length = 1000)
    private String simulatedConfirmation; // SMS & Email simulated receipt

    private LocalDateTime createdAt;

    public AppointmentBooking() {
        this.createdAt = LocalDateTime.now();
        this.status = "booked";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Alias for templates using appointmentId
    public String getAppointmentId() {
        return id;
    }

    public void setAppointmentId(String appointmentId) {
        this.id = appointmentId;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public String getBranchName() {
        return branchName != null ? branchName : branch_id;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    // Alias for templates using userName
    public String getUserName() {
        return customerName;
    }

    public void setUserName(String userName) {
        this.customerName = userName;
    }

    public String getCustomeremail() {
        return customeremail;
    }

    public void setCustomeremail(String customeremail) {
        this.customeremail = customeremail;
    }

    // Alias for templates using email
    public String getEmail() {
        return customeremail;
    }

    public void setEmail(String email) {
        this.customeremail = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getAppointmentDuration() {
        return appointmentDuration != null ? appointmentDuration : "30 min";
    }

    public void setAppointmentDuration(String appointmentDuration) {
        this.appointmentDuration = appointmentDuration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSimulatedConfirmation() {
        return simulatedConfirmation;
    }

    public void setSimulatedConfirmation(String simulatedConfirmation) {
        this.simulatedConfirmation = simulatedConfirmation;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
