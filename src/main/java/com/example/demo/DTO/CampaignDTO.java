package com.example.demo.DTO;

import com.example.demo.entity.Campaign;

import java.util.UUID;

public class CampaignDTO {

    public CampaignDTO() {}

    public CampaignDTO(Campaign campaign) {
        setId(campaign.getId());
        setTitle(campaign.getTitle());
        setDescription(campaign.getDescription());
        setLocation(String.valueOf(campaign.getLocation()));
        setDate(campaign.getDate());
        setDonationGoal(campaign.getDonationGoal());
        setCategory(campaign.getCategory());
        setFeatured(campaign.isFeatured());
        setMainimage(campaign.getMainimage());
    }

    private UUID id;
    private String title;
    private String description;
    private String location;
    private String date;
    private Double donationGoal;
    private String category;
    private boolean isFeatured = false;
    private String mainimage;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public String getMainimage() {
        return mainimage;
    }

    public void setMainimage(String mainimage) {
        this.mainimage = mainimage;
    }
}
