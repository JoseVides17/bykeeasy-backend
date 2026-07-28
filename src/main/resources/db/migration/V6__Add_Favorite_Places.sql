-- V6__Add_Favorite_Places.sql

CREATE TABLE favorite_places (
    id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    name VARCHAR(100) NOT NULL,
    address TEXT NOT NULL,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    icon_type VARCHAR(20) DEFAULT 'LOCATION' -- HOME, WORK, GYM, etc.
);

CREATE INDEX idx_favorite_places_user_id ON favorite_places(user_id);
