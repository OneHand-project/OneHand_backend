package com.example.demo.entity;

import com.example.demo.DTO.UserProfileDTO;
import jakarta.persistence.*;


@Entity(name = "users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    @Column(unique = true,nullable = false)
    private String username;
    @Column(unique = true,nullable = false)
    private String email;
    private String password;
    private String phoneNumber;
    private boolean emailVerified;
    private boolean phoneVerified;

    @Enumerated(EnumType.STRING)
    private Role role;  // This can be ADMIN, PARTICIPANT, GUEST, etc.

    public User() {
    }

//    public User(Long id, String firstName, String lastName, String username, String email, String password, String phoneNumber, boolean emailVerified, boolean phoneVerified, Role role) {
//        this.id = id;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.username = username;
//        this.email = email;
//        this.password = password;
//        this.phoneNumber = phoneNumber;
//        this.emailVerified = emailVerified;
//        this.phoneVerified = phoneVerified;
//        this.role = role;
//    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public boolean isPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    public UserProfileDTO mapToDTO() {
        UserProfileDTO dto = new UserProfileDTO();
        dto.setFirstName(this.firstName);
        dto.setLastName(this.lastName);
        dto.setPassword(this.password);
        return dto;
    }

}
