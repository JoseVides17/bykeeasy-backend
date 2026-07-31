package com.bykeeasy.infrastructure.adapter.out.persistence;

import com.bykeeasy.application.port.out.DriverRepositoryPort;
import com.bykeeasy.domain.model.Driver;
import com.bykeeasy.domain.model.UserRole;
import com.bykeeasy.infrastructure.adapter.out.persistence.entity.DriverEntity;
import com.bykeeasy.infrastructure.adapter.out.persistence.entity.UserEntity;
import com.bykeeasy.infrastructure.adapter.out.persistence.entity.WalletEntity;
import com.bykeeasy.infrastructure.adapter.out.persistence.repository.SpringDataDriverRepository;
import com.bykeeasy.infrastructure.adapter.out.persistence.repository.SpringDataUserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class DriverPersistenceAdapter implements DriverRepositoryPort {

    private final SpringDataDriverRepository driverRepository;
    private final SpringDataUserRepository userRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    @Transactional
    public Driver save(Driver driver) {
        UserEntity user = userRepository.findById(driver.getId())
                .orElseGet(() -> {
                    UserEntity ue = new UserEntity();
                    ue.setId(driver.getId());
                    ue.setEmail(driver.getEmail());
                    ue.setPassword(driver.getPassword());
                    ue.setRole(UserRole.DRIVER);
                    return ue;
                });
        
        user.setFullName(driver.getName());
        user.setPhone(driver.getPhone());
        user.setProfileImageUrl(driver.getProfileImageUrl());
        user.setRating(driver.getQualification());
                
        DriverEntity entity = user.getDriver();
        if (entity == null) {
            entity = new DriverEntity();
            entity.setUser(user);
            user.setDriver(entity);
        }
        
        // Actualizar datos del conductor
        entity.setStatus(driver.getStatus());
        entity.setVerificationStatus(driver.getVerificationStatus());
        entity.setLicenseImageUrl(driver.getLicenseImageUrl());
        entity.setSoatImageUrl(driver.getSoatImageUrl());
        entity.setPropertyCardImageUrl(driver.getPropertyCardImageUrl());
        
        // Ensure wallet
        if (user.getWallet() == null) {
            WalletEntity wallet = new WalletEntity();
            wallet.setId(UUID.randomUUID().toString());
            wallet.setUser(user);
            wallet.setBalance(java.math.BigDecimal.ZERO);
            user.setWallet(wallet);
        }
        
        // Atomic merge
        UserEntity managedUser = entityManager.merge(user);
        return PersistenceMapper.toDomain(managedUser.getDriver());
    }

    @Override
    public Optional<Driver> findById(String id) {
        return driverRepository.findById(id).map(PersistenceMapper::toDomain);
    }

    @Override
    public Optional<Driver> findByEmail(String email) {
        return driverRepository.findByUser_Email(email).map(PersistenceMapper::toDomain);
    }
}
