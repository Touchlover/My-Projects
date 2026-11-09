package com.customer_appointment.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.customer_appointment.model.AppointmentBooking;
import com.customer_appointment.model.Branch;
import com.customer_appointment.model.Customer;
import com.customer_appointment.repository.AppointmentRepo;
import com.customer_appointment.service.AppointmentService;

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private AppointmentService appointmentService;

    // Landing Page
    @GetMapping("/")
    public String getIndexPage(Model model) {
        model.addAttribute("branches", appointmentService.getAllBranches());
        return "home";
    }

    // Appointment Booking Form
    @GetMapping("/appointment")
    public String getAppointmentPage(Model model) {
        if (!model.containsAttribute("appointment")) {
            model.addAttribute("appointment", new AppointmentBooking());
        }
        model.addAttribute("branches", appointmentService.getAllBranches());
        return "appointment";
    }

    // Process Appointment Booking & Display Simulated Confirmation
    @PostMapping("/appointment")
    public String postAppointment(Model model, @ModelAttribute AppointmentBooking appointmentBooking) {
        try {
            boolean saved = appointmentService.saveData(appointmentBooking);
            if (saved) {
                model.addAttribute("appointment", appointmentBooking);
                model.addAttribute("success", "Appointment successfully scheduled! Reference ID: " + appointmentBooking.getId());
                return "confirmation";
            } else {
                model.addAttribute("error", "Unable to schedule appointment. Please verify details.");
                model.addAttribute("branches", appointmentService.getAllBranches());
                return "appointment";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error processing request: " + e.getMessage());
            model.addAttribute("branches", appointmentService.getAllBranches());
            return "appointment";
        }
    }

    // View Confirmation directly by ID
    @GetMapping("/confirmation/{id}")
    public String getConfirmationPage(@PathVariable("id") String id, Model model) {
        Optional<AppointmentBooking> aptOpt = appointmentService.findById(id);
        if (aptOpt.isPresent()) {
            model.addAttribute("appointment", aptOpt.get());
            return "confirmation";
        }
        return "redirect:/search";
    }

    // Admin Dashboard
    @GetMapping("/dashboard")
    public String getDashboardPage(Model model) {
        List<AppointmentBooking> appointments = appointmentService.findAppointmentBookings();
        model.addAttribute("appointments", appointments);
        model.addAttribute("totalAppointments", appointmentRepo.count());
        model.addAttribute("totalBooked", appointmentRepo.countByStatusIgnoreCase("booked") + appointmentRepo.countByStatusIgnoreCase("confirmed"));
        model.addAttribute("totalPending", appointmentRepo.countByStatusIgnoreCase("pending"));
        model.addAttribute("totalCanceled", appointmentRepo.countByStatusIgnoreCase("cancelled") + appointmentRepo.countByStatusIgnoreCase("canceled"));
        return "dashboard";
    }

    // Admin Update Appointment Status
    @PostMapping("/dashboard/update-status")
    public String updateStatus(@RequestParam("appointmentId") String appointmentId,
                               @RequestParam("status") String status,
                               RedirectAttributes redirectAttributes) {
        boolean updated = appointmentService.updateStatus(appointmentId, status);
        if (updated) {
            redirectAttributes.addFlashAttribute("successMessage", "Status for " + appointmentId + " updated to " + status.toUpperCase());
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update appointment " + appointmentId);
        }
        return "redirect:/dashboard";
    }

    // Admin Delete Appointment
    @GetMapping("/dashboard/delete/{id}")
    public String deleteAppointment(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        boolean deleted = appointmentService.deleteAppointment(id);
        if (deleted) {
            redirectAttributes.addFlashAttribute("successMessage", "Appointment " + id + " deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Appointment " + id + " could not be deleted.");
        }
        return "redirect:/dashboard";
    }

    // Customer Registration Page
    @GetMapping({"/customer/register", "/customer/create", "/register"})
    public String getRegisterPage(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer";
    }

    // Handle Customer Registration
    @PostMapping("/customer/create")
    public String postCustomerCreate(Model model, @ModelAttribute Customer customer) {
        if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
            model.addAttribute("error", "Email is required.");
            return "customer";
        }

        boolean exists = appointmentService.findByCustomerEmail(customer.getEmail());
        if (exists) {
            model.addAttribute("exist", "An account with " + customer.getEmail() + " already exists. Please log in.");
            return "customer";
        }

        if (appointmentService.createUserAccount(customer)) {
            model.addAttribute("success", "Your account has been created successfully! You can now log in.");
        } else {
            model.addAttribute("error", "Account creation failed. Please try again.");
        }
        return "customer";
    }

    // Customer Login Page
    @GetMapping({"/customer/login", "/user/login", "/login"})
    public String getCustomerLoginPage(Model model) {
        model.addAttribute("customer", new Customer());
        return "customerlogin";
    }

    // Handle Customer Login
    @PostMapping("/customer/login")
    public String postCustomerLogin(Model model, @ModelAttribute Customer customer) {
        boolean valid = appointmentService.customerLogin(customer.getEmail(), customer.getPassword());
        if (valid) {
            return "redirect:/appointment";
        } else {
            model.addAttribute("error", "Invalid customer email or password. Please try again.");
            return "customerlogin";
        }
    }

    // Admin Login Page
    @GetMapping("/admin/login")
    public String getAdminLoginPage(Model model) {
        model.addAttribute("customer", new Customer());
        return "admin";
    }

    // Handle Admin Login
    @PostMapping("/admin/login")
    public String postAdminLogin(Model model, @ModelAttribute Customer customer) {
        boolean valid = appointmentService.adminLogin(customer.getEmail(), customer.getPassword());
        if (valid) {
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Invalid administrator credentials.");
            return "admin";
        }
    }

    // Search Page (Check Status)
    @GetMapping("/search")
    public String getSearchPage(Model model) {
        model.addAttribute("searched", false);
        return "search";
    }

    // Process Search by Reference ID
    @PostMapping("/search")
    public String searchAppointment(@RequestParam("appointmentId") String appointmentId, Model model) {
        model.addAttribute("searched", true);
        model.addAttribute("queryId", appointmentId);
        Optional<AppointmentBooking> appointmentResult = appointmentService.findById(appointmentId);

        if (appointmentResult.isPresent()) {
            model.addAttribute("appointment", appointmentResult.get());
        } else {
            model.addAttribute("error", "No appointment found matching reference ID '" + appointmentId + "'.");
        }
        return "search";
    }
}
