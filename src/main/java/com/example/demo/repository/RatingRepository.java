package com.example.demo.repository;

import com.example.demo.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RatingRepository extends JpaRepository<Rating,Long> {
    List<Rating> findByCampaignId(UUID campaignId);

}
