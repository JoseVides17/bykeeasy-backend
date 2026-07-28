package com.bykeeasy.application.port.in;

import com.bykeeasy.domain.model.UserRole;
import java.io.InputStream;

public interface UpdateProfileUseCase {
    void updateProfile(String userId, UserRole role, String fullName, String phone, 
                       InputStream profileImage, String profileImageName);
    
    void updateDriverDocuments(String driverId, 
                               InputStream license, String licenseName,
                               InputStream soat, String soatName,
                               InputStream propertyCard, String propertyCardName);
}
