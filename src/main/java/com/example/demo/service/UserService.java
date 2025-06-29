package com.example.demo.service;

import com.example.demo.DTO.UserProfileDTO;
import com.example.demo.ResourceNotFoundException;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserService {

    //private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    @Transactional
    public User createUser(User user) {
        if (
            userRepository.existsByEmail(user.getEmail()) ||
            userRepository.existsByUsername(user.getUsername())
        ) {
            throw new RuntimeException(
                "User with email or username already exists"
            );
        }
        return userRepository.save(user); // Hibernate should commit the transaction here
    }

    public boolean deleteUser(UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public UserProfileDTO getUserProfile(UUID id) {
        return userRepository
            .findById(id)
            .map(User::mapToDTO) // or user -> user.mapToDTO()
            .orElseThrow(() ->
                new ResourceNotFoundException("User not found with id: " + id)
            );
    }

    public UserProfileDTO updateUserProfile(
        UUID id,
        UserProfileDTO updatedProfile
    ) {
        User user = userRepository
            .findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException("User not found with id: " + id)
            );
        user.setFirstName(updatedProfile.getFirstName());
        user.setLastName(updatedProfile.getLastName());
        if (
            updatedProfile.getPassword() != null &&
            !updatedProfile.getPassword().isEmpty()
        ) {
            user.setPassword(updatedProfile.getPassword());
        }
        User updatedUser = userRepository.save(user);
        return updatedUser.mapToDTO();
    }

    public User login(String userName, String password) {
        Optional<User> userOpt = userRepository.findByUsername(userName);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
