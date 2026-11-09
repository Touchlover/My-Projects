package com.customer_appointment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "branches")
public class Branch {

    @Id
    private String id;
    private String branchname;
    private String city;
    private String address;
    private String phone;

    public Branch() {
    }

    public Branch(String id, String branchname, String city, String address, String phone) {
        this.id = id;
        this.branchname = branchname;
        this.city = city;
        this.address = address;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public String getBranchName() {
        return branchname;
    }

    public void setBranchName(String branchName) {
        this.branchname = branchName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
