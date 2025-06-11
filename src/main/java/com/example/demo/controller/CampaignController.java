package com.example.demo.controller;

import com.example.demo.DTO.CampaignDTO;
import com.example.demo.entity.Campaign;
import com.example.demo.service.CampaignService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/campaign")
public class CampaignController {

    @Autowired
    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping
    public List<Campaign> getAllCampaigns() {
        return campaignService.getAllCampaigns();
    }

    // Get campaign by ID
    @GetMapping("/{id}")
    public ResponseEntity<Campaign> getCampaignById(@PathVariable Long id) {
        Optional<Campaign> campaign = campaignService.getCampaignById(id);
        return campaign
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<Campaign> getCampaignByTitle(
        @PathVariable String title
    ) {
        Optional<Campaign> campaign = campaignService.getCampaignByTitle(title);
        return campaign
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new campaign
    @PostMapping("/{organizerId}")
    public ResponseEntity<Campaign> createCampaign(
        @PathVariable Long organizerId,
        @ModelAttribute CampaignDTO campaignDTO,
        @RequestParam(value = "isFeatured", required = false) String isFeatured,
        @RequestPart(
            value = "mainimage",
            required = false
        ) MultipartFile mainimage
    ) {
        Campaign camp = new Campaign(
            campaignDTO.getTitle(),
            campaignDTO.getDescription(),
            campaignDTO.getLocation(),
            campaignDTO.getDate(),
            campaignDTO.getDonationGoal(),
            campaignDTO.getCategory()
        );
        if (isFeatured != null) camp.setFeatured(true);
        if (mainimage != null && !mainimage.isEmpty()) {
            try {
                byte[] imagebytes = mainimage.getBytes();
                camp.setMainimage(imagebytes);
            } catch (IOException e) {
                // Handle error reading bytes from MultipartFile
                throw new RuntimeException("Failed to read image bytes", e);
            }
        }
        Campaign created = campaignService.createCampaign(camp, organizerId);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/featured")
    public List<CampaignDTO> GetFeaturedCampaigns() {
        List<Campaign> campaigns = campaignService.GetFeaturedCampaigns();
        List<CampaignDTO> campaignDTOS = new ArrayList<>();
        for (Campaign camp : campaigns) {
            if (camp.getMainimage() != null) {
                String base64img = Base64.getEncoder()
                    .encodeToString(camp.getMainimage());

                campaignDTOS.add(new CampaignDTO(camp, base64img));
            } else {
                campaignDTOS.add(new CampaignDTO(camp));
            }
        }
        return campaignDTOS;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable Long id) {
        campaignService.deleteCampaign(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter/category")
    public ResponseEntity<List<Campaign>> filterByCategory(
        @RequestParam String category
    ) {
        return ResponseEntity.ok(
            campaignService.getCampaignsByCategory(category)
        );
    }

    @GetMapping("/filter/organizer")
    public ResponseEntity<List<Campaign>> filterByOrganizer(
        @RequestParam Long organizerId
    ) {
        return ResponseEntity.ok(
            campaignService.getCampaignsByOrganizer(organizerId)
        );
    }
}
