package com.example.petstore.controller;

import com.example.petstore.dto.ApiResponse;
import com.example.petstore.entity.User;
import com.example.petstore.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody User user) {
        try {
            User savedUser = userService.registerUser(user);
            Map<String, Object> response = new HashMap<>();
            response.put("user", savedUser);
            response.put("message", "Registration successful");
            return ResponseEntity.ok(new ApiResponse(true, "User registered successfully", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestParam String email) {
        return performLogin(email);
    }

    @GetMapping("/login")
    public ResponseEntity<ApiResponse> loginGet(@RequestParam String email) {
        return performLogin(email);
    }

    private ResponseEntity<ApiResponse> performLogin(String email) {
        try {
            Optional<User> user = userService.findByEmail(email);
            if (user.isPresent() && user.get().isActive()) {
                Map<String, Object> response = new HashMap<>();
                response.put("user", user.get());
                response.put("isAdmin", user.get().getRole() == User.Role.ADMIN);
                return ResponseEntity.ok(new ApiResponse(true, "Login successful", response));
            } else {
                return ResponseEntity.badRequest().body(new ApiResponse(false, "Invalid credentials or inactive account", null));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<ApiResponse> getProfile(@PathVariable Long userId) {
        try {
            Optional<User> user = userService.findById(userId);
            if (user.isPresent()) {
                return ResponseEntity.ok(new ApiResponse(true, "Profile retrieved successfully", user.get()));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }
}
