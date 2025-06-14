package com.example.demo.controller;

import com.example.demo.entity.Campaign;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {
    @Autowired
    private WishlistService wishlistService;
    @Autowired
    private UserRepository userRepository;
    private User findUserById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


    @PostMapping()
    public ResponseEntity<String> addToWishlist(@RequestParam long userId, @RequestParam UUID campaignId){
        wishlistService.addToWishlist(userId, campaignId);
        return ResponseEntity.ok("Campaign Add to Wishlist");
    }
    @GetMapping("/list/{userId}")
    public ResponseEntity<List<Campaign>> getWishlist(@PathVariable long userId){
        User user = findUserById(userId);
        List<Campaign> wishlist = wishlistService.getWishlistForUser(user);
        return ResponseEntity.ok(wishlist);
    }
    @DeleteMapping()
    public ResponseEntity<String> deleteFromWishlist(@RequestParam long userId, @RequestParam UUID campaignId){
        User user = findUserById(userId);
        wishlistService.deleteFromWishlist(user,campaignId);
        return ResponseEntity.ok("Campaign removed from your wishlist");
    }
}
