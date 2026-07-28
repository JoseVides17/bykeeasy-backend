package com.bykeeasy.infrastructure.adapter.out.persistence;

import com.bykeeasy.application.port.out.PassengerRepositoryPort;
import com.bykeeasy.domain.model.Passenger;
import com.bykeeasy.domain.model.UserRole;
import com.bykeeasy.infrastructure.adapter.out.persistence.entity.PassengerEntity;
import com.bykeeasy.infrastructure.adapter.out.persistence.entity.UserEntity;
import com.bykeeasy.infrastructure.adapter.out.persistence.repository.SpringDataPassengerRepository;
import com.bykeeasy.infrastructure.adapter.out.persistence.repository.SpringDataUserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class PassengerPersistenceAdapter implements PassengerRepositoryPort {

    private final SpringDataPassengerRepository passengerRepository;
    private final SpringDataUserRepository userRepository;

    @Override
    public Passenger save(Passenger passenger) {
        UserEntity user = userRepository.findById(passenger.getId())
                .orElseGet(() -> {
                    UserEntity ue = new UserEntity();
                    ue.setId(passenger.getId());
                    ue.setEmail(passenger.getEmail());
                    ue.setPassword(passenger.getPassword());
                    ue.setRole(UserRole.PASSENGER);
                    return ue;
                });
        
        user.setFullName(passenger.getName());
        user.setPhone(passenger.getPhone());
        user.setProfileImageUrl(passenger.getProfileImageUrl());
        userRepository.save(user);
                
        PassengerEntity entity = PersistenceMapper.toEntity(passenger);
        entity.setUser(user);
        
        PassengerEntity saved = passengerRepository.save(entity);
        return PersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<Passenger> findById(String id) {
        return passengerRepository.findById(id).map(PersistenceMapper::toDomain);
    }

    @Override
    public Optional<Passenger> findByEmail(String email) {
        return passengerRepository.findByUser_Email(email).map(PersistenceMapper::toDomain);
    }
}
