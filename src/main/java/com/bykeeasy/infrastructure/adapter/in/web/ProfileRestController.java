package com.bykeeasy.infrastructure.adapter.in.web;

import com.bykeeasy.application.port.in.RateUserUseCase;
import com.bykeeasy.application.port.in.UpdateProfileUseCase;
import com.bykeeasy.domain.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/users/profile")
@RequiredArgsConstructor
public class ProfileRestController {

    private final UpdateProfileUseCase updateProfileUseCase;
    private final RateUserUseCase rateUserUseCase;

    @PatchMapping
    public ResponseEntity<Void> updateProfile(
            @RequestParam String userId,
            @RequestParam UserRole role,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) MultipartFile profileImage
    ) throws IOException {
        
        updateProfileUseCase.updateProfile(
                userId, 
                role, 
                fullName, 
                phone, 
                profileImage != null ? profileImage.getInputStream() : null,
                profileImage != null ? profileImage.getOriginalFilename() : null
        );
        
        return ResponseEntity.ok().build();
    }

    @PostMapping("/documents")
    public ResponseEntity<Void> updateDocuments(
            @RequestParam String driverId,
            @RequestParam(required = false) MultipartFile license,
            @RequestParam(required = false) MultipartFile soat,
            @RequestParam(required = false) MultipartFile propertyCard
    ) throws IOException {
        
        updateProfileUseCase.updateDriverDocuments(
                driverId,
                license != null ? license.getInputStream() : null,
                license != null ? license.getOriginalFilename() : null,
                soat != null ? soat.getInputStream() : null,
                soat != null ? soat.getOriginalFilename() : null,
                propertyCard != null ? propertyCard.getInputStream() : null,
                propertyCard != null ? propertyCard.getOriginalFilename() : null
        );
        
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/rate")
    public ResponseEntity<Void> rateUser(
            @PathVariable String userId,
            @RequestParam double rating
    ) {
        rateUserUseCase.rateUser(userId, rating);
        return ResponseEntity.ok().build();
    }
}
