package com.customer_appointment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    private String id;
    private String branch_id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private String phone;
    private String role; // "CUSTOMER" or "ADMIN"

    public Customer() {
    }

    public Customer(String id, String first_name, String last_name, String email, String password, String role) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String firstName) {
        this.first_name = firstName;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String lastName) {
        this.last_name = lastName;
    }

    // Name compatibility helper for forms sending single "name" field
    public String getName() {
        if (first_name != null && last_name != null) {
            return first_name + " " + last_name;
        }
        return first_name != null ? first_name : "";
    }

    public void setName(String name) {
        if (name != null) {
            String[] parts = name.trim().split("\\s+", 2);
            this.first_name = parts[0];
            if (parts.length > 1) {
                this.last_name = parts[1];
            } else {
                this.last_name = "";
            }
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
