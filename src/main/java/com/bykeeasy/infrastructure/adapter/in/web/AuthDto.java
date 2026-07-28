package com.bykeeasy.infrastructure.adapter.in.web;

import com.bykeeasy.domain.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AuthDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        private String email;
        private String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterRequest {
        private String fullName;
        private String email;
        private String password;
        private String phone;
        private UserRole role;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthResponse {
        private UserDto user;
        private String token;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserDto {
        private String id;
        private String fullName;
        private String email;
        private String phone;
        private UserRole role;
        private boolean active;
        private double rating;
        private int numberOfReviews;
        private String profileImageUrl;
        private String licenseImageUrl;
        private String soatImageUrl;
        private String propertyCardImageUrl;
    }
}
