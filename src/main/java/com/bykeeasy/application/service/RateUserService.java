package com.bykeeasy.application.service;

import com.bykeeasy.application.port.in.RateUserUseCase;
import com.bykeeasy.infrastructure.adapter.out.persistence.entity.UserEntity;
import com.bykeeasy.infrastructure.adapter.out.persistence.repository.SpringDataUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class RateUserService implements RateUserUseCase {

    private final SpringDataUserRepository userRepository;

    @Override
    @Transactional
    public void rateUser(String userId, double ratingRecibido) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado para calificar"));

        double oldRating = user.getRating();
        int totalReviews = user.getNumberOfReviews();

        // Fórmula de promedio ponderado
        double newRating = ((oldRating * totalReviews) + ratingRecibido) / (totalReviews + 1);

        user.setRating(newRating);
        user.setNumberOfReviews(totalReviews + 1);

        userRepository.save(user);
    }
}
