package com.example.demo.repository;

import com.example.demo.entity.Campaign;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    Optional<Campaign> findByTitle(String title);
    List<Campaign> findByCategoryIgnoreCase(String category);
    List<Campaign> findByOrganizerId(Long organizerId);
}
