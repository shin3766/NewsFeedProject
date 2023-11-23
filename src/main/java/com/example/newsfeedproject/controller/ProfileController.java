package com.example.newsfeedproject.controller;


import com.example.newsfeedproject.dto.profiledto.ProfileRequestDto;
import com.example.newsfeedproject.dto.profiledto.ProfileResponseDto;
import com.example.newsfeedproject.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{id}")
    public ProfileResponseDto getProfileById(@PathVariable Long id) {
        return profileService.getProfile(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateProfile(@PathVariable Long id, @RequestBody ProfileRequestDto requestDto) {
        try {
            profileService.updateProfile(id, requestDto);
            return ResponseEntity.ok("Profile updated successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profile not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update profile");
        }
    }

}
