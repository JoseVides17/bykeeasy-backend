package com.bykeeasy.infrastructure.adapter.out.persistence;

import com.bykeeasy.application.port.out.PassengerRepositoryPort;
import com.bykeeasy.domain.model.Passenger;
import com.bykeeasy.domain.model.UserRole;
import com.bykeeasy.infrastructure.adapter.out.persistence.entity.PassengerEntity;
import com.bykeeasy.infrastructure.adapter.out.persistence.entity.UserEntity;
import com.bykeeasy.infrastructure.adapter.out.persistence.entity.WalletEntity;
import com.bykeeasy.infrastructure.adapter.out.persistence.repository.SpringDataPassengerRepository;
import com.bykeeasy.infrastructure.adapter.out.persistence.repository.SpringDataUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class PassengerPersistenceAdapter implements PassengerRepositoryPort {

    private final SpringDataPassengerRepository passengerRepository;
    private final SpringDataUserRepository userRepository;

    @Override
    @Transactional
    public Passenger save(Passenger passenger) {
        boolean isNewUser = !userRepository.existsById(passenger.getId());
        
        UserEntity user = userRepository.findById(passenger.getId())
                .orElseGet(() -> {
                    UserEntity ue = new UserEntity();
                    ue.setId(passenger.getId());
                    ue.setEmail(passenger.getEmail());
                    ue.setPassword(passenger.getPassword());
                    ue.setRole(UserRole.PASSENGER);
                    ue.setNew(true);
                    return ue;
                });
        
        user.setFullName(passenger.getName());
        user.setPhone(passenger.getPhone());
        user.setProfileImageUrl(passenger.getProfileImageUrl());
        user.setNew(isNewUser);
                
        PassengerEntity entity = PersistenceMapper.toEntity(passenger);
        entity.setUser(user);
        entity.setNew(isNewUser);
        user.setPassenger(entity);
        
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
        return PersistenceMapper.toDomain(savedUser.getPassenger());
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
