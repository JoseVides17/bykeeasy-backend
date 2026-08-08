package com.bykeeasy.infrastructure.adapter.out.persistence;

import com.bykeeasy.domain.model.*;
import com.bykeeasy.infrastructure.adapter.out.persistence.entity.*;

public class PersistenceMapper {

    private PersistenceMapper() {
    }

    public static User toDomain(UserEntity entity) {
        if (entity == null) return null;
        return new User(
                entity.getId(),
                entity.getFullName(),
                entity.getPhone(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getRole(),
                entity.isActive(),
                entity.getRating(),
                entity.getNumberOfReviews(),
                entity.getProfileImageUrl(),
                entity.getDriver() != null ? entity.getDriver().getLicenseImageUrl() : null,
                entity.getDriver() != null ? entity.getDriver().getSoatImageUrl() : null,
                entity.getDriver() != null ? entity.getDriver().getPropertyCardImageUrl() : null
        );
    }
    
    public static UserEntity toEntity(User domain) {
        if (domain == null) return null;
        UserEntity entity = new UserEntity();
        entity.setId(domain.getId());
        entity.setEmail(domain.getEmail());
        entity.setPassword(domain.getPasswordHash());
        entity.setFullName(domain.getFullName());
        entity.setPhone(domain.getPhone());
        entity.setProfileImageUrl(domain.getProfileImageUrl());
        entity.setRole(domain.getRole());
        entity.setActive(domain.isActive());
        entity.setRating(domain.getRating());
        entity.setNumberOfReviews(domain.getNumberOfReviews());
        return entity;
    }

    public static Driver toDomain(DriverEntity entity) {
        if (entity == null) return null;
        java.util.List<Vehicle> vehicles = new java.util.ArrayList<>();
        if (entity.getVehicles() != null) {
            for (VehicleEntity ve : entity.getVehicles()) {
                vehicles.add(new Vehicle(
                        ve.getId(),
                        ve.getLicensePlate(),
                        ve.getVehicleType(),
                        ve.getVehicleModel(),
                        ve.getVehicleColor(),
                        ve.getVehicleBrand(),
                        ve.getImageUrl()
                ));
            }
        }
        
        Coordinate location = (entity.getCurrentLatitude() != null && entity.getCurrentLongitude() != null)
                ? new Coordinate(entity.getCurrentLatitude(), entity.getCurrentLongitude())
                : null;
                
        Driver driver = new Driver(
                entity.getUserId(),
                entity.getUser().getFullName(),
                entity.getUser().getPhone(),
                entity.getUser().getEmail(),
                entity.getUser().getPassword(),
                (int) entity.getUser().getRating(),
                entity.getStatus(),
                vehicles,
                location
        );
        driver.setVerificationStatus(entity.getVerificationStatus());
        driver.setProfileImageUrl(entity.getUser().getProfileImageUrl());
        driver.setLicenseImageUrl(entity.getLicenseImageUrl());
        driver.setSoatImageUrl(entity.getSoatImageUrl());
        driver.setPropertyCardImageUrl(entity.getPropertyCardImageUrl());
        return driver;
    }

    public static DriverEntity toEntity(Driver domain) {
        if (domain == null) return null;
        DriverEntity entity = new DriverEntity();
        entity.setUserId(domain.getId());
        entity.setStatus(domain.getStatus());
        entity.setVerificationStatus(domain.getVerificationStatus());
        entity.setLicenseImageUrl(domain.getLicenseImageUrl());
        entity.setSoatImageUrl(domain.getSoatImageUrl());
        entity.setPropertyCardImageUrl(domain.getPropertyCardImageUrl());
        
        if (domain.getVehicles() != null) {
            java.util.List<VehicleEntity> ves = new java.util.ArrayList<>();
            for (Vehicle v : domain.getVehicles()) {
                VehicleEntity ve = new VehicleEntity();
                ve.setId(v.getId());
                ve.setLicensePlate(v.getLicensePlate());
                ve.setVehicleType(v.getType());
                ve.setVehicleModel(v.getModel());
                ve.setVehicleColor(v.getColor());
                ve.setVehicleBrand(v.getBrand());
                ve.setImageUrl(v.getImageUrl());
                ve.setDriver(entity);
                ves.add(ve);
            }
            entity.setVehicles(ves);
        }
        
        if (domain.getCurrentLocation() != null) {
            entity.setCurrentLatitude(domain.getCurrentLocation().getLatitude());
            entity.setCurrentLongitude(domain.getCurrentLocation().getLongitude());
        }
        return entity;
    }

    public static Passenger toDomain(PassengerEntity entity) {
        if (entity == null) return null;
        return new Passenger(
                entity.getUserId(),
                entity.getUser().getFullName(),
                entity.getUser().getPhone(),
                entity.getUser().getEmail(),
                entity.getUser().getPassword(),
                (int) entity.getUser().getRating(),
                entity.getUser().getProfileImageUrl()
        );
    }

    public static PassengerEntity toEntity(Passenger domain) {
        if (domain == null) return null;
        PassengerEntity entity = new PassengerEntity();
        entity.setUserId(domain.getId());
        return entity;
    }

    public static Wallet toDomain(WalletEntity entity) {
        if (entity == null) return null;
        String userId = (entity.getUser() != null) ? entity.getUser().getId() : null;
        return new Wallet(entity.getId(), userId, entity.getBalance());
    }

    public static WalletEntity toEntity(Wallet domain) {
        if (domain == null) return null;
        WalletEntity entity = new WalletEntity();
        entity.setId(domain.getId());
        entity.setBalance(domain.getBalance());
        return entity;
    }

    public static Transaction toDomain(TransactionEntity entity) {
        if (entity == null) return null;
        return new Transaction(
                entity.getId(),
                entity.getWalletId(),
                entity.getAmount(),
                entity.getType(),
                entity.getDescription(),
                entity.getTimestamp()
        );
    }

    public static TransactionEntity toEntity(Transaction domain) {
        if (domain == null) return null;
        TransactionEntity entity = new TransactionEntity();
        entity.setId(domain.getId());
        entity.setWalletId(domain.getWalletId());
        entity.setAmount(domain.getAmount());
        entity.setType(domain.getType());
        entity.setDescription(domain.getDescription());
        entity.setTimestamp(domain.getTimestamp());
        return entity;
    }
    
    public static Journey toJourneyDomain(JourneyEntity entity) {
        if (entity == null) return null;
        return new Journey(
                entity.getId(),
                entity.getPassengerId(),
                entity.getPassengerName(),
                entity.getDriverId(),
                new Coordinate(entity.getLatOrigin(), entity.getLonOrigin()),
                entity.getOriginAddress(),
                new Coordinate(entity.getLatDestination(), entity.getLonDestination()),
                entity.getDestinationAddress(),
                entity.getFare(),
                entity.getCommission(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getDriverName(),
                entity.getVehiclePlate(),
                entity.getVehicleModel()
        );
    }

    public static JourneyEntity toJourneyEntity(Journey journey) {
        if (journey == null) return null;
        JourneyEntity entity = new JourneyEntity();
        entity.setId(journey.getId());
        entity.setPassengerId(journey.getPassengerId());
        entity.setPassengerName(journey.getPassengerName());
        entity.setDriverId(journey.getDriverId());
        entity.setLatOrigin(journey.getOrigin().getLatitude());
        entity.setLonOrigin(journey.getOrigin().getLongitude());
        entity.setOriginAddress(journey.getOriginAddress());
        entity.setLatDestination(journey.getDestination().getLatitude());
        entity.setLonDestination(journey.getDestination().getLongitude());
        entity.setDestinationAddress(journey.getDestinationAddress());
        entity.setFare(journey.getFare());
        entity.setCommission(journey.getCommission());
        entity.setStatus(journey.getStatus());
        entity.setCreatedAt(journey.getCreatedAt());
        entity.setDriverName(journey.getDriverName());
        entity.setVehiclePlate(journey.getVehiclePlate());
        entity.setVehicleModel(journey.getVehicleModel());

        return entity;
    }

    public static Offer toDomain(OfferEntity entity) {
        if (entity == null) return null;
        return new Offer(
                entity.getId(),
                entity.getJourneyId(),
                entity.getDriverId(),
                entity.getProposedFare(),
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }

    public static OfferEntity toEntity(Offer domain) {
        if (domain == null) return null;
        OfferEntity entity = new OfferEntity();
        entity.setId(domain.getId());
        entity.setJourneyId(domain.getJourneyId());
        entity.setDriverId(domain.getDriverId());
        entity.setProposedFare(domain.getProposedFare());
        entity.setStatus(domain.getStatus());
        entity.setCreatedAt(domain.getCreatedAt());
        return entity;
    }

    public static Vehicle toDomain(VehicleEntity entity) {
        if (entity == null) return null;
        return new Vehicle(
                entity.getId(),
                entity.getLicensePlate(),
                entity.getVehicleType(),
                entity.getVehicleModel(),
                entity.getVehicleColor(),
                entity.getVehicleBrand(),
                entity.getImageUrl()
        );
    }

    public static VehicleEntity toEntity(Vehicle domain) {
        if (domain == null) return null;
        VehicleEntity entity = new VehicleEntity();
        entity.setId(domain.getId());
        entity.setLicensePlate(domain.getLicensePlate());
        entity.setVehicleType(domain.getType());
        entity.setVehicleModel(domain.getModel());
        entity.setVehicleColor(domain.getColor());
        entity.setVehicleBrand(domain.getBrand());
        entity.setImageUrl(domain.getImageUrl());
        return entity;
    }

    public static FavoritePlace toDomain(FavoritePlaceEntity entity) {
        if (entity == null) return null;
        return new FavoritePlace(
                entity.getId(),
                entity.getUser().getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getIconType()
        );
    }

    public static FavoritePlaceEntity toEntity(FavoritePlace toDomain) {
        if (toDomain == null) return null;
        FavoritePlaceEntity entity = new FavoritePlaceEntity();
        entity.setId(toDomain.getId());
        entity.setName(toDomain.getName());
        entity.setAddress(toDomain.getAddress());
        entity.setLatitude(toDomain.getLatitude());
        entity.setLongitude(toDomain.getLongitude());
        entity.setIconType(toDomain.getIconType());
        return entity;
    }
}
