package com.bykeeasy.application.service;

import com.bykeeasy.application.port.in.AuthUseCase;
import com.bykeeasy.application.port.out.DriverRepositoryPort;
import com.bykeeasy.application.port.out.PassengerRepositoryPort;
import com.bykeeasy.application.port.out.WalletRepositoryPort;
import com.bykeeasy.domain.model.Driver;
import com.bykeeasy.domain.model.Passenger;
import com.bykeeasy.domain.model.User;
import com.bykeeasy.domain.model.UserRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public class AuthService implements AuthUseCase {

    private final PassengerRepositoryPort passengerRepository;
    private final DriverRepositoryPort driverRepository;
    private final WalletRepositoryPort walletRepository;

    public AuthService(PassengerRepositoryPort passengerRepository, DriverRepositoryPort driverRepository, WalletRepositoryPort walletRepository) {
        this.passengerRepository = passengerRepository;
        this.driverRepository = driverRepository;
        this.walletRepository = walletRepository;
    }

    @Override
    public User login(String email, String password) {
        var passengerOpt = passengerRepository.findByEmail(email);
        if (passengerOpt.isPresent()) {
            Passenger p = passengerOpt.get();
            if (p.getPassword().equals(password)) {
                return new User(
                        p.getId(),
                        p.getName(),
                        p.getPhone(),
                        p.getEmail(),
                        p.getPassword(),
                        UserRole.PASSENGER,
                        true,
                        4.5, 0,
                        p.getProfileImageUrl(),
                        null, null, null
                );
            }
        }

        var driverOpt = driverRepository.findByEmail(email);
        if (driverOpt.isPresent()) {
            Driver d = driverOpt.get();
            if (d.getPassword().equals(password)) {
                return new User(
                        d.getId(),
                        d.getName(),     // fullName
                        d.getPhone(),    // phone
                        d.getEmail(),    // email
                        d.getPassword(), // passwordHash
                        UserRole.DRIVER,
                        true,
                        4.5, 0,
                        d.getProfileImageUrl(),
                        d.getLicenseImageUrl(),
                        d.getSoatImageUrl(),
                        d.getPropertyCardImageUrl()
                );
            }
        }

        throw new RuntimeException("Credenciales inválidas");
    }

    @Override
    @Transactional
    public User register(String fullName, String email, String password, String phone, UserRole role) {
        // 1. Check if email exists
        if (passengerRepository.findByEmail(email).isPresent() || driverRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("El correo electrónico ya está en uso");
        }

        String id = UUID.randomUUID().toString();

        if (role == UserRole.PASSENGER) {
            Passenger p = new Passenger(id, fullName, phone, email, password, 0, null);
            // The adapter now handles User and Wallet creation atomically
            Passenger saved = passengerRepository.save(p);

            return new User(
                    saved.getId(),
                    saved.getName(),
                    saved.getPhone(),
                    saved.getEmail(),
                    saved.getPassword(),
                    UserRole.PASSENGER,
                    true,
                    4.5, 0,
                    null,
                    null, null, null
            );
        } else if (role == UserRole.DRIVER) {
            Driver d = new Driver(id, fullName, phone, email, password, 0, null, null, null);
            // The adapter now handles User and Wallet creation atomically
            Driver saved = driverRepository.save(d);

            return new User(
                    saved.getId(),
                    saved.getName(),
                    saved.getPhone(),
                    saved.getEmail(),
                    saved.getPassword(),
                    UserRole.DRIVER,
                    true,
                    4.5, 0,
                    null,
                    null, null, null
            );
        }
        throw new RuntimeException("Rol no soportado para el registro");
    }

    @Override
    public User getUserById(String userId) {
        var passengerOpt = passengerRepository.findById(userId);
        if (passengerOpt.isPresent()) {
            Passenger p = passengerOpt.get();
            return new User(p.getId(), p.getName(), p.getPhone(), p.getEmail(), p.getPassword(), UserRole.PASSENGER, true, 4.5, 0, p.getProfileImageUrl(), null, null, null);
        }

        var driverOpt = driverRepository.findById(userId);
        if (driverOpt.isPresent()) {
            Driver d = driverOpt.get();
            return new User(d.getId(), d.getName(), d.getPhone(), d.getEmail(), d.getPassword(), UserRole.DRIVER, true, 4.5, 0, d.getProfileImageUrl(), d.getLicenseImageUrl(), d.getSoatImageUrl(), d.getPropertyCardImageUrl());
        }

        throw new RuntimeException("Usuario no encontrado");
    }
}
