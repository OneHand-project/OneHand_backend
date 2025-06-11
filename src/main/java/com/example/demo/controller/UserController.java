package com.example.demo.controller;

import com.example.demo.DTO.LoginRequestDTO;
import com.example.demo.DTO.UserProfileDTO;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// @CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestAttribute User user) {
        try {
            User savedUser = userService.createUser(user);
            return ResponseEntity.ok(savedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // In case user already exists
        }
    }

    // Delete user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfileDTO> updateUserProfile(
        @PathVariable Long id,
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
