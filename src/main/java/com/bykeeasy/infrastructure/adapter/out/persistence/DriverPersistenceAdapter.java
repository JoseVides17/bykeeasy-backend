package com.bykeeasy.infrastructure.adapter.out.persistence;

import com.bykeeasy.application.port.out.DriverRepositoryPort;
import com.bykeeasy.domain.model.Driver;
import com.bykeeasy.domain.model.UserRole;
import com.bykeeasy.infrastructure.adapter.out.persistence.entity.DriverEntity;
import com.bykeeasy.infrastructure.adapter.out.persistence.entity.UserEntity;
import com.bykeeasy.infrastructure.adapter.out.persistence.repository.SpringDataDriverRepository;
import com.bykeeasy.infrastructure.adapter.out.persistence.repository.SpringDataUserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class DriverPersistenceAdapter implements DriverRepositoryPort {

    private final SpringDataDriverRepository driverRepository;
    private final SpringDataUserRepository userRepository;

    @Override
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

        // 1. Guardamos y RECUPERAMOS la referencia gestionada por Spring Data
        UserEntity savedUser = userRepository.save(user);

        // 2. Mappeamos la entidad principal
        DriverEntity entity = PersistenceMapper.toEntity(driver);

        // 3. Enlazamos la referencia ÚNICA
        entity.setUser(savedUser);
        entity.setUserId(savedUser.getId());

        DriverEntity saved = driverRepository.save(entity);
        return PersistenceMapper.toDomain(saved);
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