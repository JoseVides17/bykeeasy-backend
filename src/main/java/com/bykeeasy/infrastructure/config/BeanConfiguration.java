package com.bykeeasy.infrastructure.config;

import com.bykeeasy.infrastructure.adapter.out.persistence.*;
import com.bykeeasy.infrastructure.adapter.out.payment.MockPaymentAdapter;
import com.bykeeasy.infrastructure.adapter.out.persistence.repository.*;
import com.bykeeasy.application.port.in.AuthUseCase;
import com.bykeeasy.application.port.in.DestinationUseCase;
import com.bykeeasy.application.port.in.DriverStatsUseCase;
import com.bykeeasy.application.port.in.FavoritePlaceUseCase;
import com.bykeeasy.application.port.in.NegotiationUseCase;
import com.bykeeasy.application.port.in.PassengerStatsUseCase;
import com.bykeeasy.application.port.in.PlaceSearchUseCase;
import com.bykeeasy.application.port.in.RateUserUseCase;
import com.bykeeasy.application.port.in.RequestJourneyUseCase;
import com.bykeeasy.application.port.in.TakeJourneyUseCase;
import com.bykeeasy.application.port.in.UpdateLocationDriverUseCase;
import com.bykeeasy.application.port.in.UpdateProfileUseCase;
import com.bykeeasy.application.port.out.*;
import com.bykeeasy.application.service.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JourneyRepositoryPort journeyRepositoryPort(SpringDataJourneyRepository springDataJourneyRepository) {
        return new JourneyPersistenceAdapter(springDataJourneyRepository);
    }

    @Bean
    public DriverRepositoryPort driverRepositoryPort(SpringDataDriverRepository springDataDriverRepository, SpringDataUserRepository userRepository) {
        return new DriverPersistenceAdapter(springDataDriverRepository, userRepository, entityManager);
    }

    @Bean
    public PassengerRepositoryPort passengerRepositoryPort(SpringDataPassengerRepository springDataPassengerRepository, SpringDataUserRepository userRepository) {
        return new PassengerPersistenceAdapter(springDataPassengerRepository, userRepository, entityManager);
    }

    @Bean
    public OfferRepositoryPort offerRepositoryPort(SpringDataOfferRepository springDataOfferRepository) {
        return new OfferPersistenceAdapter(springDataOfferRepository);
    }

    @Bean
    public WalletRepositoryPort walletRepositoryPort(SpringDataWalletRepository walletRepository, SpringDataTransactionRepository transactionRepository, SpringDataUserRepository userRepository) {
        return new WalletPersistenceAdapter(walletRepository, transactionRepository, userRepository, entityManager);
    }

    @Bean
    public WalletUseCase walletUseCase(WalletRepositoryPort walletRepositoryPort, PaymentGatewayPort paymentGatewayPort) {
        return new WalletService(walletRepositoryPort, paymentGatewayPort);
    }

    @Bean
    public RequestJourneyUseCase requestJourneyUseCase(JourneyRepositoryPort journeyRepositoryPort) {
        return new RequestJourneyService(journeyRepositoryPort);
    }

    @Bean
    public TakeJourneyUseCase takeJourneyUseCase(JourneyRepositoryPort journeyRepositoryPort, WalletUseCase walletUseCase) {
        return new TakeJourneyService(journeyRepositoryPort, walletUseCase);
    }

    @Bean
    public UpdateLocationDriverUseCase updateLocationDriverUseCase(DriverRepositoryPort driverRepositoryPort) {
        return new UpdateLocationDriverService(driverRepositoryPort);
    }

    @Bean
    public AuthUseCase authUseCase(PassengerRepositoryPort passengerRepositoryPort, DriverRepositoryPort driverRepositoryPort, WalletRepositoryPort walletRepositoryPort) {
        return new AuthService(passengerRepositoryPort, driverRepositoryPort, walletRepositoryPort);
    }

    @Bean
    public NegotiationUseCase negotiationUseCase(OfferRepositoryPort offerRepositoryPort, JourneyRepositoryPort journeyRepositoryPort) {
        return new NegotiationService(offerRepositoryPort, journeyRepositoryPort);
    }

    @Bean
    public DriverStatsUseCase driverStatsUseCase(JourneyRepositoryPort journeyRepositoryPort, DriverRepositoryPort driverRepositoryPort, WalletUseCase walletUseCase) {
        return new DriverStatsService(journeyRepositoryPort, driverRepositoryPort, walletUseCase);
    }

    @Bean
    public PassengerStatsUseCase passengerStatsUseCase(JourneyRepositoryPort journeyRepositoryPort, PassengerRepositoryPort passengerRepositoryPort) {
        return new PassengerStatsService(journeyRepositoryPort, passengerRepositoryPort);
    }

    @Bean
    public DestinationUseCase destinationUseCase(JourneyRepositoryPort journeyRepositoryPort) {
        return new DestinationService(journeyRepositoryPort);
    }

    @Value("${serpapi.api.key}")
    private String serpApiApiKey;

    @Bean
    public PlaceSearchUseCase placeSearchUseCase() {
        return new PlaceSearchService(serpApiApiKey);
    }

    @Bean
    public FavoritePlaceRepositoryPort favoritePlaceRepositoryPort(SpringDataFavoritePlaceRepository favoritePlaceRepository, SpringDataUserRepository userRepository) {
        return new FavoritePlacePersistenceAdapter(favoritePlaceRepository, userRepository);
    }

    @Bean
    public FavoritePlaceUseCase favoritePlaceUseCase(FavoritePlaceRepositoryPort favoritePlaceRepositoryPort) {
        return new FavoritePlaceService(favoritePlaceRepositoryPort);
    }

    @Bean
    public FileStoragePort fileStoragePort() {
        return new com.bykeeasy.infrastructure.adapter.out.storage.LocalStorageAdapter();
    }

    @Bean
    public PaymentGatewayPort paymentGatewayPort() {
        return new com.bykeeasy.infrastructure.adapter.out.payment.MockPaymentAdapter();
    }

    @Bean
    public UpdateProfileUseCase updateProfileUseCase(PassengerRepositoryPort passengerRepository, DriverRepositoryPort driverRepository, FileStoragePort fileStorage) {
        return new UpdateProfileService(passengerRepository, driverRepository, fileStorage);
    }

    @Bean
    public VehicleRepositoryPort vehicleRepositoryPort(SpringDataVehicleRepository vehicleRepository, SpringDataDriverRepository driverRepository) {
        return new VehiclePersistenceAdapter(vehicleRepository, driverRepository, entityManager);
    }

    @Bean
    public VehicleUseCase vehicleUseCase(VehicleRepositoryPort vehicleRepositoryPort, FileStoragePort fileStorage) {
        return new com.bykeeasy.application.service.VehicleService(vehicleRepositoryPort, fileStorage);
    }

    @Bean
    public RateUserUseCase rateUserUseCase(SpringDataUserRepository userRepository) {
        return new RateUserService(userRepository);
    }
}
