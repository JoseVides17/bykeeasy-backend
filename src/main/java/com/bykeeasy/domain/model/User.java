package com.bykeeasy.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private final String id;
    private final String fullName;
    private final String phone;
    private final String email;
    private final String passwordHash;
    private final UserRole role;
    private boolean active;
    private final double rating;
    private final int numberOfReviews;
    private String profileImageUrl;
    private String licenseImageUrl;
    private String soatImageUrl;
    private String propertyCardImageUrl;

    public User(String id, String fullName, String phone, String email, String passwordHash,
                UserRole role, boolean active, double rating, int numberOfReviews, String profileImageUrl,
                String licenseImageUrl, String soatImageUrl, String propertyCardImageUrl) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.active = active;
        this.rating = rating;
        this.numberOfReviews = numberOfReviews;
        this.profileImageUrl = profileImageUrl;
        this.licenseImageUrl = licenseImageUrl;
        this.soatImageUrl = soatImageUrl;
        this.propertyCardImageUrl = propertyCardImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void setLicenseImageUrl(String licenseImageUrl) { this.licenseImageUrl = licenseImageUrl; }
    public void setSoatImageUrl(String soatImageUrl) { this.soatImageUrl = soatImageUrl; }
    public void setPropertyCardImageUrl(String propertyCardImageUrl) { this.propertyCardImageUrl = propertyCardImageUrl; }

    public void setActive(boolean active) {
        this.active = active;
    }
}