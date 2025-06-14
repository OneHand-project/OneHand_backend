package com.example.demo.service;

import com.example.demo.entity.Campaign;
import com.example.demo.entity.User;
import com.example.demo.entity.Wishlist;
import com.example.demo.repository.CampaignRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WishlistRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class WishlistService {
    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    public void addToWishlist(long userId, UUID campaignId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new RuntimeException("Campaign not found"));

        boolean alreadyExists = wishlistRepository.existsByUserAndCampaign(user, campaign);
        if (alreadyExists) {
            throw new RuntimeException("Campaign already in wishlist");
        }

        Wishlist wishlist = new Wishlist(user, campaign);
        wishlistRepository.save(wishlist);
    }
    public List<Campaign> getWishlistForUser(User user) {
        return wishlistRepository.findByUser(user).stream()
                .map(Wishlist::getCampaign)
                .collect(Collectors.toList());
    }
    public void deleteFromWishlist(User user, UUID campaignId){
        wishlistRepository.deleteByUserAndCampaignId(user, campaignId);
    }

}
