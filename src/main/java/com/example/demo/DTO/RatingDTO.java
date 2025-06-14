package com.example.demo.DTO;

import java.util.UUID;

public class RatingDTO {
    private int score;
    private String comment;
    private UUID userId;
    private UUID campaignId;

    public RatingDTO() {
    }

    public RatingDTO(int score, String comment, UUID userId, UUID campaignId) {
        this.score = score;
        this.comment = comment;
        this.userId = userId;
        this.campaignId = campaignId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(UUID campaignId) {
        this.campaignId = campaignId;
    }
}
