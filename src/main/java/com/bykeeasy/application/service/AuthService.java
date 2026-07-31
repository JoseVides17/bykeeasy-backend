package com.bykeeasy.application.service;

import com.bykeeasy.application.port.in.AuthUseCase;
import com.bykeeasy.application.port.out.DriverRepositoryPort;
import com.bykeeasy.application.port.out.PassengerRepositoryPort;
import com.bykeeasy.application.port.out.WalletRepositoryPort;
import com.bykeeasy.domain.model.Driver;
import com.bykeeasy.domain.model.Passenger;
import com.bykeeasy.domain.model.User;
import com.bykeeasy.domain.model.UserRole;
import com.bykeeasy.domain.model.Wallet;
import org.springframework.transaction.annotation.Transactional; // Importante

import java.math.BigDecimal;
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
                        p.getProfileImageUrl()
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
                        d.getProfileImageUrl()
                );
            }
        }

        throw new RuntimeException("Credenciales inválidas");
    }

    @Override
    @Transactional // <--- Mantiene ambas operaciones dentro de la misma transacción de BD
    public User register(String fullName, String email, String password, String phone, UserRole role) {
        String id = UUID.randomUUID().toString();

        if (role == UserRole.PASSENGER) {
            // 1. Guardar primero el Pasajero
            Passenger p = new Passenger(id, fullName, phone, email, password, 0, null);
            Passenger saved = passengerRepository.save(p);

            // 2. Crear la billetera DESPUÉS de persistir el usuario
            walletRepository.save(new Wallet(UUID.randomUUID().toString(), saved.getId(), BigDecimal.ZERO));

            return new User(
                    saved.getId(),
                    saved.getName(),
                    saved.getPhone(),
                    saved.getEmail(),
                    saved.getPassword(),
                    UserRole.PASSENGER,
                    true,
                    4.5,
                    0,
                    null
            );
        } else if (role == UserRole.DRIVER) {
            // 1. Guardar primero el Conductor
            Driver d = new Driver(id, fullName, phone, email, password, 0, null, null, null);
            Driver saved = driverRepository.save(d);

            // 2. Crear la billetera DESPUÉS de persistir el usuario
            walletRepository.save(new Wallet(UUID.randomUUID().toString(), saved.getId(), BigDecimal.ZERO));

            return new User(
                    saved.getId(),
                    saved.getName(),
                    saved.getPhone(),
                    saved.getEmail(),
                    saved.getPassword(),
                    UserRole.DRIVER,
                    true,
                    4.5,
                    0,
                    null
            );
        }
        throw new RuntimeException("Rol no soportado para el registro");
    }

    @Override
    public User getUserById(String userId) {
        var passengerOpt = passengerRepository.findById(userId);
        if (passengerOpt.isPresent()) {
            Passenger p = passengerOpt.get();
            return new User(p.getId(), p.getName(), p.getPhone(), p.getEmail(), p.getPassword(), UserRole.PASSENGER, true, 4.5, 0, p.getProfileImageUrl());
        }

        var driverOpt = driverRepository.findById(userId);
        if (driverOpt.isPresent()) {
            Driver d = driverOpt.get();
            return new User(d.getId(), d.getName(), d.getPhone(), d.getEmail(), d.getPassword(), UserRole.DRIVER, true, 4.5, 0, d.getProfileImageUrl());
        }

        throw new RuntimeException("Usuario no encontrado");
    }
}