-- V2__Add_Journey_Details.sql
ALTER TABLE journeys ADD COLUMN passenger_name VARCHAR(255);
ALTER TABLE journeys ADD COLUMN origin_address TEXT;
ALTER TABLE journeys ADD COLUMN destination_address TEXT;
