package com.example.demo.repository;

import com.example.demo.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    Optional<Campaign> findById(UUID id);
    Optional<Campaign> findByTitle(String title);
    List<Campaign> findByOrganizerId(UUID id);
    boolean existsById(UUID id);
    void deleteById(UUID id);
    List<Campaign> findByCategoryIgnoreCase(String category);
//    List<Campaign> findByOrganizerId(Long organizerId);
    List<Campaign> findByFeatured(boolean featured);
}
