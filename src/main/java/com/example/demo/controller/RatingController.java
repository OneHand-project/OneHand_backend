package com.example.demo.controller;

import com.example.demo.DTO.RatingDTO;
import com.example.demo.entity.Rating;
import com.example.demo.service.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/rating")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }
    @PostMapping
    public ResponseEntity<Rating> addRating(@RequestBody RatingDTO ratingDTO) {
        Rating rating = ratingService.addRating(ratingDTO);
        return new ResponseEntity<>(rating, HttpStatus.CREATED);
    }
}
