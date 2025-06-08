package com.example.demo.service;

import com.example.demo.DTO.CampaignDTO;
import com.example.demo.entity.Campaign;
import com.example.demo.entity.User;
import com.example.demo.repository.CampaignRepository;
import com.example.demo.repository.UserRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
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

    public Campaign createCampaign(CampaignDTO campaign, Long organizerId) {
        Campaign camp = modelmapper.map(campaign, Campaign.class);
        try {
            camp.setMainimage(campaign.getMainimage().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        User organizer = userRepository
            .findById(organizerId)
            .orElseThrow(() -> new RuntimeException("Organizer not found"));
        camp.setOrganizer(organizer);
        return campaignRepository.save(camp);
    }

    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAll();
    }

    public Optional<Campaign> getCampaignById(Long id) {
        return campaignRepository.findById(id);
    }

    public Optional<Campaign> getCampaignByTitle(String title) {
        return campaignRepository.findByTitle(title);
    }

    public boolean deleteCampaign(Long id) {
        if (campaignRepository.existsById(id)) {
            campaignRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Campaign> getCampaignsByCategory(String category) {
        return campaignRepository.findByCategoryIgnoreCase(category);
    }

    public List<Campaign> getCampaignsByOrganizer(Long organizerId) {
        return campaignRepository.findByOrganizerId(organizerId);
    }
}
