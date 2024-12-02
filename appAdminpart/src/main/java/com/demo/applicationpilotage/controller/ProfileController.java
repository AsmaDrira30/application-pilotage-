package com.demo.applicationpilotage.controller;

import com.demo.applicationpilotage.User;
import com.demo.applicationpilotage.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    // Get user profile
    @GetMapping("/{id}")
    public User getUserProfile(@PathVariable Long id) {
        return profileService.getUserProfile(id);
    }

    @PutMapping("/insert/{id}")
    public String updateUserProfile(@PathVariable Long id, @RequestBody User updatedProfile) {
        User updatedUser  = profileService.updateUserProfile(id, updatedProfile);
        if (updatedUser  != null) {
            return "Profile updated successfully."; 
        }
        return "User  not found."; 
    }}
