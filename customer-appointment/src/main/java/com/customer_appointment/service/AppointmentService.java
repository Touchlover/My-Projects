package com.customer_appointment.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customer_appointment.model.AppointmentBooking;
import com.customer_appointment.model.Branch;
import com.customer_appointment.model.Customer;
import com.customer_appointment.repository.AppointmentRepo;
import com.customer_appointment.repository.BranchRepo;
import com.customer_appointment.repository.CustomerRepo;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private BranchRepo branchRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @PostConstruct
    public void initData() {
        // Seed default branches if empty
        if (branchRepo.count() == 0) {
            branchRepo.save(new Branch("BR-01", "Capitec Foreshore Branch", "Cape Town", "11 Adderley Street, Cape Town CBD", "+27 21 418 1000"));
            branchRepo.save(new Branch("BR-02", "Capitec Sandton City Branch", "Johannesburg", "Sandton City Mall, 83 Rivonia Rd, Sandhurst", "+27 11 883 2000"));
            branchRepo.save(new Branch("BR-03", "Capitec Durban Central Branch", "Durban", "Anton Lembede Street, Durban Central", "+27 31 305 3000"));
            branchRepo.save(new Branch("BR-04", "Capitec Menlyn Park Branch", "Pretoria", "Menlyn Park Shopping Centre, Pretoria", "+27 12 368 4000"));
            branchRepo.save(new Branch("BR-05", "Capitec Gateway Branch", "Umhlanga", "Gateway Theatre of Shopping, Umhlanga", "+27 31 566 5000"));
        }

        // Seed default admin and sample customer if empty
        if (customerRepo.count() == 0) {
            Customer admin = new Customer("CUST-001", "Admin", "User", "admin@capitecbank.co.za", "admin123", "ADMIN");
            admin.setPhone("+27 82 123 4567");
            customerRepo.save(admin);

            Customer sampleUser = new Customer("CUST-002", "Sipho", "Nkosi", "sipho.nkosi@example.com", "user123", "CUSTOMER");
            sampleUser.setPhone("+27 71 987 6543");
            customerRepo.save(sampleUser);
        }

        // Seed sample appointments if empty so the dashboard has rich data
        if (appointmentRepo.count() == 0) {
            AppointmentBooking apt1 = new AppointmentBooking();
            apt1.setId("CPT-58291");
            apt1.setCustomerName("Sipho Nkosi");
            apt1.setCustomeremail("sipho.nkosi@example.com");
            apt1.setPhone("+27 71 987 6543");
            apt1.setAddress("45 Main Rd, Sea Point, Cape Town");
            apt1.setAccountNumber("1589347201");
            apt1.setBranch_id("BR-01");
            apt1.setBranchName("Capitec Foreshore Branch");
            apt1.setServiceType("Loan Consultation");
            apt1.setAppointmentDate(LocalDate.now().plusDays(1));
            apt1.setAppointmentTime("10:00 AM");
            apt1.setAppointmentDuration("30 min");
            apt1.setStatus("booked");
            apt1.setSimulatedConfirmation(buildConfirmationMessage(apt1));
            appointmentRepo.save(apt1);

            AppointmentBooking apt2 = new AppointmentBooking();
            apt2.setId("CPT-61942");
            apt2.setCustomerName("Naledi Molefe");
            apt2.setCustomeremail("naledi.m@example.com");
            apt2.setPhone("+27 83 555 7890");
            apt2.setAddress("12 Nelson Mandela Blvd, Sandton");
            apt2.setAccountNumber("1938472910");
            apt2.setBranch_id("BR-02");
            apt2.setBranchName("Capitec Sandton City Branch");
            apt2.setServiceType("Account Opening");
            apt2.setAppointmentDate(LocalDate.now().plusDays(2));
            apt2.setAppointmentTime("02:30 PM");
            apt2.setAppointmentDuration("45 min");
            apt2.setStatus("pending");
            apt2.setSimulatedConfirmation(buildConfirmationMessage(apt2));
            appointmentRepo.save(apt2);

            AppointmentBooking apt3 = new AppointmentBooking();
            apt3.setId("CPT-73014");
            apt3.setCustomerName("David Van Der Merwe");
            apt3.setCustomeremail("david.vdm@example.com");
            apt3.setPhone("+27 84 222 3456");
            apt3.setAddress("78 Florida Rd, Morningside, Durban");
            apt3.setAccountNumber("1284759302");
            apt3.setBranch_id("BR-03");
            apt3.setBranchName("Capitec Durban Central Branch");
            apt3.setServiceType("Financial Planning");
            apt3.setAppointmentDate(LocalDate.now().minusDays(1));
            apt3.setAppointmentTime("11:15 AM");
            apt3.setAppointmentDuration("60 min");
            apt3.setStatus("canceled");
            apt3.setSimulatedConfirmation(buildConfirmationMessage(apt3));
            appointmentRepo.save(apt3);
        }
    }

    public boolean createUserAccount(Customer customer) {
        if (customer.getId() == null || customer.getId().trim().isEmpty()) {
            customer.setId("CUST-" + (1000 + new Random().nextInt(9000)));
        }
        if (customer.getRole() == null || customer.getRole().trim().isEmpty()) {
            customer.setRole("CUSTOMER");
        }
        return customerRepo.save(customer) != null;
    }

    public boolean findByCustomerEmail(String email) {
        if (email == null) return false;
        return customerRepo.existsByEmail(email.trim());
    }

    public boolean adminLogin(String email, String password) {
        if (email == null || password == null) return false;
        // Default super admin fallback
        if ("admin@capitecbank.co.za".equalsIgnoreCase(email.trim()) && "admin123".equals(password.trim())) {
            return true;
        }
        Optional<Customer> userOpt = customerRepo.findByEmailAndPassword(email.trim(), password.trim());
        return userOpt.isPresent() && "ADMIN".equalsIgnoreCase(userOpt.get().getRole());
    }

    public boolean customerLogin(String email, String password) {
        if (email == null || password == null) return false;
        return customerRepo.findByEmailAndPassword(email.trim(), password.trim()).isPresent();
    }

    public boolean saveData(AppointmentBooking appointmentBooking) {
        if (appointmentBooking.getId() == null || appointmentBooking.getId().trim().isEmpty()) {
            appointmentBooking.setId(generateRandomString());
        }

        if (appointmentBooking.getAppointmentDate() == null) {
            appointmentBooking.setAppointmentDate(LocalDate.now().plusDays(1));
        }

        if (appointmentBooking.getAppointmentTime() == null || appointmentBooking.getAppointmentTime().trim().isEmpty()) {
            appointmentBooking.setAppointmentTime("10:00 AM");
        }

        if (appointmentBooking.getStatus() == null || appointmentBooking.getStatus().trim().isEmpty()) {
            appointmentBooking.setStatus("booked");
        }

        // Resolve branch name if branch_id is present
        if (appointmentBooking.getBranch_id() != null && !appointmentBooking.getBranch_id().isEmpty()) {
            branchRepo.findById(appointmentBooking.getBranch_id()).ifPresent(b -> {
                appointmentBooking.setBranchName(b.getBranchName());
            });
        }
        if (appointmentBooking.getBranchName() == null || appointmentBooking.getBranchName().isEmpty()) {
            appointmentBooking.setBranchName("Capitec Foreshore Branch");
        }

        // Generate simulated confirmation for SMS and Email
        appointmentBooking.setSimulatedConfirmation(buildConfirmationMessage(appointmentBooking));

        appointmentRepo.save(appointmentBooking);
        return true;
    }

    public List<AppointmentBooking> findAppointmentBookings() {
        return appointmentRepo.findAll();
    }

    public List<Branch> getAllBranches() {
        return branchRepo.findAll();
    }

    public Optional<AppointmentBooking> findById(String id) {
        if (id == null) return Optional.empty();
        return appointmentRepo.findById(id.trim());
    }

    public boolean updateStatus(String appointmentId, String newStatus) {
        Optional<AppointmentBooking> aptOpt = appointmentRepo.findById(appointmentId);
        if (aptOpt.isPresent()) {
            AppointmentBooking apt = aptOpt.get();
            apt.setStatus(newStatus);
            appointmentRepo.save(apt);
            return true;
        }
        return false;
    }

    public boolean deleteAppointment(String appointmentId) {
        if (appointmentRepo.existsById(appointmentId)) {
            appointmentRepo.deleteById(appointmentId);
            return true;
        }
        return false;
    }

    public static String buildConfirmationMessage(AppointmentBooking apt) {
        String name = apt.getCustomerName() != null ? apt.getCustomerName() : "Valued Customer";
        String branch = apt.getBranchName() != null ? apt.getBranchName() : "Selected Branch";
        String date = apt.getAppointmentDate() != null ? apt.getAppointmentDate().toString() : "Scheduled Date";
        String time = apt.getAppointmentTime() != null ? apt.getAppointmentTime() : "10:00 AM";
        String service = apt.getServiceType() != null ? apt.getServiceType() : "Banking Service";
        String ref = apt.getId() != null ? apt.getId() : "N/A";

        return "[SIMULATED SMS SENT TO: " + (apt.getPhone() != null ? apt.getPhone() : "Customer Phone") + "]\n"
                + "Capitec Bank: Hi " + name + ", your appointment (Ref: " + ref + ") at " + branch + " on "
                + date + " at " + time + " for " + service + " is confirmed. Please bring your SA ID or passport.\n\n"
                + "[SIMULATED EMAIL SENT TO: " + (apt.getCustomeremail() != null ? apt.getCustomeremail() : "Customer Email") + "]\n"
                + "Subject: Capitec Bank - Branch Appointment Confirmation [" + ref + "]\n"
                + "Dear " + name + ",\n"
                + "Thank you for scheduling your visit. Your appointment details:\n"
                + " - Reference Number: " + ref + "\n"
                + " - Branch: " + branch + "\n"
                + " - Date & Time: " + date + " @ " + time + " (" + apt.getAppointmentDuration() + ")\n"
                + " - Service: " + service + "\n"
                + "Status: CONFIRMED. We look forward to welcoming you!";
    }

    public static String generateRandomString() {
        final String PREFIX = "CPT-";
        final int RANDOM_PART_LENGTH = 5;
        StringBuilder sb = new StringBuilder(PREFIX);
        Random random = new Random();

        for (int i = 0; i < RANDOM_PART_LENGTH; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }

        return sb.toString();
    }
}
