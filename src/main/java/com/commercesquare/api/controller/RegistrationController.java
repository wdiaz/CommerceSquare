package com.commercesquare.api.controller;

import com.commercesquare.api.entity.User;
import com.commercesquare.api.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registration(@Valid @RequestBody User user, BindingResult bindingResult) {
        // Validate the input using the BindingResult
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Validation error: " + bindingResult.getFieldError().getDefaultMessage());
        }

        // Check if the email is already registered
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email address is already in use");
        }

        try {
            // Encrypt the password before saving to the database
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // Save the user to the database
            userRepository.save(user);

            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while processing the registration");
        }
    }
}
