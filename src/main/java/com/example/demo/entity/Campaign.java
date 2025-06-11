package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class Campaign {

    public Campaign() {
        // no-arg constructor for JPA
    }

    public Campaign(
        String title,
        String description,
        String location,
        String date,
        Double donationGoal,
        String category
    ) {
        setTitle(title);
        setDescription(description);
        setLocation(location);
        setDate(date);
        setDonationGoal(donationGoal);
        setCategory(category);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String location;

    private byte[] mainimage;

    private boolean featured = false;
    private String date;
    private Double donationGoal;
    private String category;

    public byte[] getMainimage() {
        return mainimage;
    }

    public void setMainimage(byte[] mainimage) {
        this.mainimage = mainimage;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User organizer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getDonationGoal() {
        return donationGoal;
    }

    public void setDonationGoal(Double donationGoal) {
        this.donationGoal = donationGoal;
    }

    public User getOrganizer() {
        return organizer;
    }

    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }
}
