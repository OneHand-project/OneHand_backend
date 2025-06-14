package com.example.demo.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Campaign {

    public Campaign() {
        // no-arg constructor for JPA
    }

    public Campaign(
        String title,
        String description,
        String location,
        LocalDate date,
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
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private String title;
    private String description;
    private String location;

    private String mainimage;
    private List<String> multpleimages;

    private boolean featured = false;
    private LocalDate date;
    private Double donationGoal;
    private String category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User organizer;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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

    public String getMainimage() {
        return mainimage;
    }

    public void setMainimage(String mainimage) {
        this.mainimage = mainimage;
    }

    public List<String> getMultpleimages() {
        return multpleimages;
    }

    public void setMultpleimages(List<String> multpleimages) {
        this.multpleimages = multpleimages;
    }
}
