package com.example.demo.controller;

import com.example.demo.DTO.CampaignDTO;
import com.example.demo.entity.Campaign;
import com.example.demo.service.CampaignService;
import com.example.demo.service.FileUploadService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/campaign")
public class CampaignController {

    private final FileUploadService uploadService;

    private final CampaignService campaignService;

    public CampaignController(
        CampaignService campaignService,
        FileUploadService uploadService
    ) {
        this.campaignService = campaignService;
        this.uploadService = uploadService;
    }

    @GetMapping
    public List<Campaign> getAllCampaigns() {
        return campaignService.getAllCampaigns();
    }

    // Get campaign by ID
    @GetMapping("/{id}")
    public ResponseEntity<Campaign> getCampaignById(@PathVariable UUID id) {
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
        @PathVariable UUID organizerId,
        @ModelAttribute CampaignDTO campaignDTO,
        @RequestParam(value = "isFeatured", required = false) String isFeatured,
        @RequestParam(
            value = "main",
            required = false
        ) MultipartFile mainimage,
        @RequestParam(value = "images",required = false) List<MultipartFile> multpleimages
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
        Campaign created = campaignService.createCampaign(camp, organizerId);
        if (mainimage != null && !mainimage.isEmpty()) {
            try {
                String fileUrl = uploadService.uploadFile(
                    mainimage,
                    String.valueOf(created.getId())
                );
                List<String> imagesurl = uploadService.uploadMultipleFiles(multpleimages,String.valueOf(created.getId()));
                created.setMainimage(fileUrl);
                created.setMultpleimages(imagesurl);
            } catch (IOException e) {
                // Handle error reading bytes from MultipartFile
                throw new RuntimeException("Failed to read image bytes", e);
            }
        }
        Campaign saved = campaignService.updateCampaign(created);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/featured")
    public List<CampaignDTO> GetFeaturedCampaigns() {
        List<Campaign> campaigns = campaignService.GetFeaturedCampaigns();
        List<CampaignDTO> campaignDTOS = new ArrayList<>();
        for (Campaign camp : campaigns) {
            campaignDTOS.add(new CampaignDTO(camp));
        }
        return campaignDTOS;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable UUID id) {
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
        @RequestParam UUID organizerId
    ) {
        return ResponseEntity.ok(
            campaignService.getCampaignsByOrganizer(organizerId)
        );
    }
}
