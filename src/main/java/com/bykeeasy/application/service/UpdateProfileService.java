package com.bykeeasy.application.service;

import com.bykeeasy.application.port.in.UpdateProfileUseCase;
import com.bykeeasy.application.port.out.DriverRepositoryPort;
import com.bykeeasy.application.port.out.FileStoragePort;
import com.bykeeasy.application.port.out.PassengerRepositoryPort;
import com.bykeeasy.domain.model.Driver;
import com.bykeeasy.domain.model.Passenger;
import com.bykeeasy.domain.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;

@RequiredArgsConstructor
public class UpdateProfileService implements UpdateProfileUseCase {

    private final PassengerRepositoryPort passengerRepository;
    private final DriverRepositoryPort driverRepository;
    private final FileStoragePort fileStorage;

    @Override
    @Transactional
    public void updateProfile(String userId, UserRole role, String fullName, String phone, 
                               InputStream profileImage, String profileImageName) {
        
        String imageUrl = null;
        if (profileImage != null) {
            imageUrl = fileStorage.store(profileImage, profileImageName, "profiles");
        }

        if (role == UserRole.PASSENGER) {
            Passenger passenger = passengerRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Passenger not found"));
            
            if (fullName != null) passenger.setName(fullName);
            if (phone != null) passenger.setPhone(phone);
            if (imageUrl != null) passenger.setProfileImageUrl(imageUrl);
            
            passengerRepository.save(passenger);
        } else if (role == UserRole.DRIVER) {
            Driver driver = driverRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Driver not found"));
            
            if (fullName != null) driver.setName(fullName);
            if (phone != null) driver.setPhone(phone);
            if (imageUrl != null) driver.setProfileImageUrl(imageUrl);
            
            driverRepository.save(driver);
        }
    }

    @Override
    @Transactional
    public void updateDriverDocuments(String driverId, 
                                       InputStream license, String licenseName,
                                       InputStream soat, String soatName,
                                       InputStream propertyCard, String propertyCardName) {
        
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        if (license != null) {
            driver.setLicenseImageUrl(fileStorage.store(license, licenseName, "documents"));
        }
        if (soat != null) {
            driver.setSoatImageUrl(fileStorage.store(soat, soatName, "documents"));
        }
        if (propertyCard != null) {
            driver.setPropertyCardImageUrl(fileStorage.store(propertyCard, propertyCardName, "documents"));
        }

        driverRepository.save(driver);
    }
}
