-- V4__Enhanced_Relations.sql

-- 1. Create a common users table
CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255),
    phone VARCHAR(50),
    role VARCHAR(20) NOT NULL, -- PASSENGER, DRIVER, ADMIN
    active BOOLEAN DEFAULT TRUE,
    rating DOUBLE PRECISION DEFAULT 4.5,
    number_of_reviews INT DEFAULT 0,
    profile_image_url VARCHAR(500)
);

-- 2. Migrate existing data to users table
-- Merging from passengers first
INSERT INTO users (id, email, password, full_name, phone, role, active, rating, number_of_reviews, profile_image_url)
SELECT id, email, password, name, phone, 'PASSENGER', TRUE, 4.5, 0, profile_image_url FROM passengers;

-- Merging from drivers (avoiding email duplicates)
INSERT INTO users (id, email, password, full_name, phone, role, active, rating, number_of_reviews, profile_image_url)
SELECT id, email, password, name, phone, 'DRIVER', TRUE, qualification, 0, profile_image_url FROM drivers
ON CONFLICT (email) DO NOTHING;

-- 3. Restructure passengers table
ALTER TABLE passengers RENAME TO passengers_old;
CREATE TABLE passengers (
    user_id VARCHAR(36) PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE
);

INSERT INTO passengers (user_id)
SELECT id FROM passengers_old;

DROP TABLE passengers_old;

-- 4. Restructure drivers table
ALTER TABLE drivers RENAME TO drivers_old;
CREATE TABLE drivers (
    user_id VARCHAR(36) PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
    status VARCHAR(50),
    verification_status VARCHAR(50),
    current_latitude DOUBLE PRECISION,
    current_longitude DOUBLE PRECISION,
    license_image_url VARCHAR(500),
    soat_image_url VARCHAR(500),
    property_card_image_url VARCHAR(500)
);

INSERT INTO drivers (user_id, status, verification_status, current_latitude, current_longitude, license_image_url, soat_image_url, property_card_image_url)
SELECT id, status, verification_status, current_latitude, current_longitude, license_image_url, soat_image_url, property_card_image_url FROM drivers_old;

-- 5. Create vehicles table
CREATE TABLE vehicles (
    id VARCHAR(36) PRIMARY KEY,
    driver_id VARCHAR(36) NOT NULL REFERENCES drivers(user_id) ON DELETE CASCADE,
    license_plate VARCHAR(20),
    vehicle_type VARCHAR(50),
    vehicle_model VARCHAR(100),
    vehicle_color VARCHAR(50),
    vehicle_brand VARCHAR(100)
);

INSERT INTO vehicles (id, driver_id, license_plate, vehicle_type, vehicle_model, vehicle_color, vehicle_brand)
SELECT COALESCE(vehicle_id, gen_random_uuid()::text), id, license_plate, vehicle_type, vehicle_model, vehicle_color, vehicle_brand
FROM drivers_old WHERE license_plate IS NOT NULL;

DROP TABLE drivers_old;

-- 6. Enhance wallets table
ALTER TABLE wallets ADD CONSTRAINT fk_wallets_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

-- 7. Enhance transactions table
ALTER TABLE transactions ADD CONSTRAINT fk_transactions_wallet FOREIGN KEY (wallet_id) REFERENCES wallets(id) ON DELETE CASCADE;

-- 8. Enhance journeys table
ALTER TABLE journeys ADD CONSTRAINT fk_journeys_passenger FOREIGN KEY (passenger_id) REFERENCES users(id);
ALTER TABLE journeys ADD CONSTRAINT fk_journeys_driver FOREIGN KEY (driver_id) REFERENCES users(id);

-- 9. Enhance offers table
ALTER TABLE offers ADD CONSTRAINT fk_offers_journey FOREIGN KEY (journey_id) REFERENCES journeys(id) ON DELETE CASCADE;
ALTER TABLE offers ADD CONSTRAINT fk_offers_driver FOREIGN KEY (driver_id) REFERENCES users(id);
