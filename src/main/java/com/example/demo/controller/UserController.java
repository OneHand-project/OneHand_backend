package com.example.demo.controller;

import com.example.demo.DTO.LoginRequestDTO;
import com.example.demo.DTO.UserProfileDTO;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

// @CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(
        UserService userService,
        PasswordEncoder passwordEncoder
    ) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        Optional<User> user = userService.getUserById(id);
        return user
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new user
    @PostMapping("/newuser")
    public ResponseEntity<User> createUser(@ModelAttribute User user) {
        try {
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword);
            User savedUser = userService.createUser(user);
            return ResponseEntity.ok(savedUser);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build(); // In case user already exists
        }
    }

    // Delete user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfileDTO> updateUserProfile(
        @PathVariable UUID id,
        @RequestBody UserProfileDTO profileDTO
    ) {
        UserProfileDTO updatedProfile = userService.updateUserProfile(
            id,
            profileDTO
        );
        System.out.println("✅ Controller method reached");
        return ResponseEntity.ok(updatedProfile);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(
        @ModelAttribute LoginRequestDTO request
    ) {
        try {
            User user = userService.login(
                request.getUserName(),
                request.getPassword()
            );
            if (user == null) {
                return ResponseEntity.status(401).body("failed");
            }
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("Auth error");
        }
    }
}
