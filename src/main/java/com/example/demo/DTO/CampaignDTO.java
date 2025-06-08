package com.example.demo.DTO;

import org.springframework.web.multipart.MultipartFile;

public class CampaignDTO {

    private String title;
    private String description;
    private MultipartFile mainimage;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getMainimage() {
        return mainimage;
    }

    public void setMainimage(MultipartFile mainimage) {
        this.mainimage = mainimage;
    }
}
