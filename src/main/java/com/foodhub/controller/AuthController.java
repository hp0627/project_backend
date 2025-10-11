package com.foodhub.controller;

import com.foodhub.model.User;
import com.foodhub.payload.ApiResponse;
import com.foodhub.payload.LoginRequest;
import com.foodhub.repository.UserRepository;
import com.foodhub.security.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body(new ApiResponse("User already exists"));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok(new ApiResponse("Signup successful"));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Login attempt for email: " + loginRequest.getEmail());

        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("User found: " + user.getEmail());
            System.out.println("Encoded password in DB: " + user.getPassword());
            System.out.println("Raw password entered: " + loginRequest.getPassword());

            boolean matches = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
            System.out.println("Password matches: " + matches);

            if (matches) {
                String token = tokenProvider.generateToken(user);

                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("id", user.getId());
                response.put("email", user.getEmail());
                response.put("full_name", user.getFullName());
                String role = user.getRole() != null ? user.getRole().toLowerCase() : "customer";
                response.put("role", role);

                return ResponseEntity.ok(response);
            }
        } else {
            System.out.println("User not found");
        }

        return ResponseEntity.status(401).body(new ApiResponse("Invalid email or password"));
    }
}