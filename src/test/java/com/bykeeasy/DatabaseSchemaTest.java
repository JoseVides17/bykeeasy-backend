package com.bykeeasy;

import com.bykeeasy.domain.model.UserRole;
import com.bykeeasy.infrastructure.adapter.out.persistence.entity.PassengerEntity;
import com.bykeeasy.infrastructure.adapter.out.persistence.entity.UserEntity;
import com.bykeeasy.infrastructure.adapter.out.persistence.repository.SpringDataPassengerRepository;
import com.bykeeasy.infrastructure.adapter.out.persistence.repository.SpringDataUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class DatabaseSchemaTest {

    @Autowired
    private SpringDataPassengerRepository passengerRepository;

    @Autowired
    private SpringDataUserRepository userRepository;

    @Test
    @Transactional
    void testPassengerSaveAndLoad() {
        String id = UUID.randomUUID().toString();
        
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setFullName("Test User");
        user.setEmail("test_" + UUID.randomUUID() + "@example.com");
        user.setPassword("password123");
        user.setPhone("123456789");
        user.setRole(UserRole.PASSENGER);
        
        PassengerEntity passenger = new PassengerEntity();
        passenger.setUser(user);
        user.setPassenger(passenger);
        
        userRepository.save(user);
        
        PassengerEntity found = passengerRepository.findById(id).orElse(null);
        assertNotNull(found);
        assertNotNull(found.getUser());
        assertEquals("password123", found.getUser().getPassword());
    }
}
