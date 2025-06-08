package com.example.demo.service;

import com.example.demo.DTO.RatingDTO;
import com.example.demo.ResourceNotFoundException;
import com.example.demo.entity.Campaign;
import com.example.demo.entity.Rating;
import com.example.demo.entity.User;
import com.example.demo.repository.CampaignRepository;
import com.example.demo.repository.RatingRepository;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final CampaignRepository campaignRepository;
    private final UserRepository userRepository;

    public RatingService(RatingRepository ratingRepository, CampaignRepository campaignRepository, UserRepository userRepository) {
        this.ratingRepository = ratingRepository;
        this.campaignRepository = campaignRepository;
        this.userRepository = userRepository;
    }
    public Rating addRating(RatingDTO ratingDTO) {
        User user = userRepository.findById(ratingDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Campaign campaign = campaignRepository.findById(ratingDTO.getCampaignId())
                .orElseThrow(() -> new RuntimeException("Campaign not found"));

        Rating rating = new Rating();
        rating.setRate(ratingDTO.getScore());
        rating.setComment(ratingDTO.getComment());
        rating.setUser(user);
        rating.setCampaign(campaign);

        return ratingRepository.save(rating);
    }


}
