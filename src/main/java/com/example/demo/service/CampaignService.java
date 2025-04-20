package com.example.demo.service;

import com.example.demo.entity.Campaign;
import com.example.demo.entity.User;
import com.example.demo.repository.CampaignRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CampaignService {
    private final CampaignRepository campaignRepository;

    public CampaignService(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }
    public Campaign createCampaign ( Campaign campaign){
        return campaignRepository.save(campaign);
    }
    public List<Campaign> getAllCampaigns(){
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
}
