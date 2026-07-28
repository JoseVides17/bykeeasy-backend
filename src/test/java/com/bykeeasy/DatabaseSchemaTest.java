package com.bykeeasy;

import com.bykeeasy.infrastructure.adapter.out.persistence.entity.PassengerEntity;
import com.bykeeasy.infrastructure.adapter.out.persistence.repository.SpringDataPassengerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class DatabaseSchemaTest {

    @Autowired
    private SpringDataPassengerRepository passengerRepository;

    @Test
    @Transactional
    void testPassengerSaveAndLoad() {
        PassengerEntity passenger = new PassengerEntity();
        passenger.setId(UUID.randomUUID().toString());
        passenger.setName("Test User");
        passenger.setEmail("test_" + UUID.randomUUID() + "@example.com");
        passenger.setPassword("password123");
        passenger.setPhone("123456789");
        passenger.setQualification(5);

        passengerRepository.save(passenger);
        
        PassengerEntity found = passengerRepository.findById(passenger.getId()).orElse(null);
        assertNotNull(found);
        assertNotNull(found.getPassword());
    }
}
