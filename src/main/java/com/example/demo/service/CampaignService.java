package com.example.demo.service;

import com.example.demo.DTO.CampaignDTO;
import com.example.demo.entity.Campaign;
import com.example.demo.entity.User;
import com.example.demo.repository.CampaignRepository;
import com.example.demo.repository.UserRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private ModelMapper modelmapper;

    private final UserRepository userRepository;

    public CampaignService(
        CampaignRepository campaignRepository,
        UserRepository userRepository
    ) {
        this.campaignRepository = campaignRepository;
        this.userRepository = userRepository;
    }

    public Campaign createCampaign(Campaign campaign, long organizerId) {
        User organizer = userRepository
            .findById(organizerId)
            .orElseThrow(() -> new RuntimeException("Organizer not found"));
        campaign.setOrganizer(organizer);
        return campaignRepository.save(campaign);
    }

    public Campaign updateCampaign(Campaign campaign){
        return campaignRepository.save(campaign);

    }

    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAll();
    }
    public List<Campaign> GetFeaturedCampaigns() {
        return campaignRepository.findByFeatured(true);
    }

    public Optional<Campaign> getCampaignById(UUID id) {
        return campaignRepository.findById(id);
    }

    public Optional<Campaign> getCampaignByTitle(String title) {
        return campaignRepository.findByTitle(title);
    }

    public boolean deleteCampaign(UUID id) {
        if (campaignRepository.existsById(id)) {
            campaignRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Campaign> getCampaignsByCategory(String category) {
        return campaignRepository.findByCategoryIgnoreCase(category);
    }

    public List<Campaign> getCampaignsByOrganizer(long organizerId) {
        return campaignRepository.findByOrganizerId(organizerId);
    }
}
