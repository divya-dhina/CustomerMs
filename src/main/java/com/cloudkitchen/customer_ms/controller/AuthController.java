package com.cloudkitchen.customer_ms.controller;


import com.cloudkitchen.customer_ms.dto.LoginRequest;
import com.cloudkitchen.customer_ms.dto.SignupRequest;
import com.cloudkitchen.customer_ms.model.Customer;
import com.cloudkitchen.customer_ms.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest request) {
        if (customerRepo.findByUsername(request.username) != null) {
            return "Username already exists!";
        }

        Customer customer = new Customer();
        customer.setUsername(request.username);
        customer.setPassword(passwordEncoder.encode(request.password));  // Encrypt here
        customer.setName(request.name);
        customer.setPhone(request.phone);
        customer.setAddress(request.address);
        customerRepo.save(customer);

        return "User registered successfully.";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        Customer customer = customerRepo.findByUsername(request.username);
        if (customer != null && passwordEncoder.matches(request.password, customer.getPassword())) {
            return "Login successful.";
        }
        return "Invalid username or password.";
    }

    @GetMapping("/oauth-success")
    public String oauthSuccess(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "Authentication failed or not available!";
        }

        OAuth2User user = (OAuth2User) authentication.getPrincipal();
        String email = user.getAttribute("email");

        Customer existing = customerRepo.findByUsername(email);
        if (existing == null) {
            Customer customer = new Customer();
            customer.setUsername(email);
            customer.setName(user.getAttribute("name"));
            customerRepo.save(customer);
        }

        return "OAuth login success for: " + email;
    }

}


