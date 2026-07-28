-- V3__Add_Profile_Images.sql
ALTER TABLE passengers ADD COLUMN profile_image_url VARCHAR(500);
ALTER TABLE drivers ADD COLUMN profile_image_url VARCHAR(500);
ALTER TABLE drivers ADD COLUMN license_image_url VARCHAR(500);
ALTER TABLE drivers ADD COLUMN soat_image_url VARCHAR(500);
ALTER TABLE drivers ADD COLUMN property_card_image_url VARCHAR(500);
