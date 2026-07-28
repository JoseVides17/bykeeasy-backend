package com.bykeeasy.infrastructure.adapter.in.web;

import com.bykeeasy.application.port.in.AuthUseCase;
import com.bykeeasy.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthUseCase authUseCase;
    private final com.bykeeasy.infrastructure.security.JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<AuthDto.AuthResponse> login(@RequestBody AuthDto.LoginRequest request) {
        User user = authUseCase.login(request.getEmail(), request.getPassword());
        String token = tokenProvider.generateToken(user.getId(), user.getEmail(), user.getRole().name());
        return ResponseEntity.ok(new AuthDto.AuthResponse(mapToDto(user), token));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthDto.AuthResponse> register(@RequestBody AuthDto.RegisterRequest request) {
        User user = authUseCase.register(
                request.getFullName(),
                request.getEmail(),
                request.getPassword(),
                request.getPhone(),
                request.getRole()
        );
        String token = tokenProvider.generateToken(user.getId(), user.getEmail(), user.getRole().name());
        return ResponseEntity.ok(new AuthDto.AuthResponse(mapToDto(user), token));
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<AuthDto.UserDto> getProfile(@PathVariable String userId) {
        // We need a way to find user by ID. 
        // AuthService should have findById.
        User user = authUseCase.getUserById(userId);
        return ResponseEntity.ok(mapToDto(user));
    }

    private AuthDto.UserDto mapToDto(User user) {
        return new AuthDto.UserDto(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole(),
                user.isActive(),
                user.getRating(),
                user.getNumberOfReviews(),
                user.getProfileImageUrl(),
                user.getLicenseImageUrl(),
                user.getSoatImageUrl(),
                user.getPropertyCardImageUrl()
        );
    }
}
