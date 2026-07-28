-- V1__Initial_Schema.sql

CREATE TABLE drivers (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255),
    phone VARCHAR(50),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    qualification INT,
    status VARCHAR(50),
    verification_status VARCHAR(50),
    vehicle_id VARCHAR(36),
    license_plate VARCHAR(20),
    vehicle_type VARCHAR(50),
    vehicle_model VARCHAR(100),
    vehicle_color VARCHAR(50),
    vehicle_brand VARCHAR(100),
    current_latitude DOUBLE PRECISION,
    current_longitude DOUBLE PRECISION
);

CREATE TABLE passengers (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255),
    phone VARCHAR(50),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    qualification INT
);

CREATE TABLE journeys (
    id VARCHAR(36) PRIMARY KEY,
    passenger_id VARCHAR(36) NOT NULL,
    driver_id VARCHAR(36),
    lat_origin DOUBLE PRECISION NOT NULL,
    lon_origin DOUBLE PRECISION NOT NULL,
    lat_destination DOUBLE PRECISION NOT NULL,
    lon_destination DOUBLE PRECISION NOT NULL,
    fare DECIMAL(12, 2) NOT NULL,
    commission DECIMAL(12, 2) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE wallets (
    id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL UNIQUE,
    balance DECIMAL(12, 2) NOT NULL DEFAULT 0
);

CREATE TABLE transactions (
    id VARCHAR(36) PRIMARY KEY,
    wallet_id VARCHAR(36) NOT NULL,
    amount DECIMAL(12, 2) NOT NULL,
    type VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    timestamp TIMESTAMP NOT NULL
);

CREATE TABLE offers (
    id VARCHAR(36) PRIMARY KEY,
    journey_id VARCHAR(36) NOT NULL,
    driver_id VARCHAR(36) NOT NULL,
    proposed_fare DECIMAL(12, 2) NOT NULL,
    status VARCHAR(50) NOT NULL, -- PENDING, ACCEPTED, REJECTED
    created_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_journeys_passenger_id ON journeys(passenger_id);
CREATE INDEX idx_journeys_driver_id ON journeys(driver_id);
CREATE INDEX idx_journeys_status ON journeys(status);
CREATE INDEX idx_transactions_wallet_id ON transactions(wallet_id);
CREATE INDEX idx_offers_journey_id ON offers(journey_id);
CREATE INDEX idx_offers_driver_id ON offers(driver_id);
