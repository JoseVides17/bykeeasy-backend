package com.bykeeasy.infrastructure.adapter.out.persistence;

import com.bykeeasy.application.port.out.DriverRepositoryPort;
import com.bykeeasy.domain.model.Driver;
import com.bykeeasy.domain.model.UserRole;
import com.bykeeasy.infrastructure.adapter.out.persistence.entity.DriverEntity;
import com.bykeeasy.infrastructure.adapter.out.persistence.entity.UserEntity;
import com.bykeeasy.infrastructure.adapter.out.persistence.entity.WalletEntity;
import com.bykeeasy.infrastructure.adapter.out.persistence.repository.SpringDataDriverRepository;
import com.bykeeasy.infrastructure.adapter.out.persistence.repository.SpringDataUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class DriverPersistenceAdapter implements DriverRepositoryPort {

    private final SpringDataDriverRepository driverRepository;
    private final SpringDataUserRepository userRepository;

    @Override
    @Transactional
    public Driver save(Driver driver) {
        boolean isNewUser = !userRepository.existsById(driver.getId());

        UserEntity user = userRepository.findById(driver.getId())
                .orElseGet(() -> {
                    UserEntity ue = new UserEntity();
                    ue.setId(driver.getId());
                    ue.setEmail(driver.getEmail());
                    ue.setPassword(driver.getPassword());
                    ue.setRole(UserRole.DRIVER);
                    ue.setNew(true);
                    return ue;
                });
        
        user.setFullName(driver.getName());
        user.setPhone(driver.getPhone());
        user.setProfileImageUrl(driver.getProfileImageUrl());
        user.setRating(driver.getQualification());
        user.setNew(isNewUser);
                
        DriverEntity entity = PersistenceMapper.toEntity(driver);
        entity.setUser(user);
        entity.setNew(isNewUser);
        user.setDriver(entity);
        
        // Ensure wallet for users
        if (user.getWallet() == null) {
            WalletEntity wallet = new WalletEntity();
            wallet.setId(UUID.randomUUID().toString());
            wallet.setUser(user);
            wallet.setBalance(java.math.BigDecimal.ZERO);
            wallet.setNew(true);
            user.setWallet(wallet);
        } else {
            user.getWallet().setNew(false);
        }
        
        UserEntity savedUser = userRepository.save(user);
        return PersistenceMapper.toDomain(savedUser.getDriver());
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
