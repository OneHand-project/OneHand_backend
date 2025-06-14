package com.example.demo.repository;

import com.example.demo.entity.Campaign;
import com.example.demo.entity.User;
import com.example.demo.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByUser(User user);
    void deleteByUserAndCampaignId(User user, UUID campaignId);
    boolean existsByUserAndCampaign(User user, Campaign campaign);

}
