package com.bykeeasy.application.port.in;

public interface TakeJourneyUseCase {
    void takeJourney(String journeyId, String driverId);
    void startRouteToPickup(String journeyId);
    void arriveAtPickup(String journeyId);
    void startJourney(String journeyId);
    void completeJourney(String journeyId);
    void cancelJourney(String journeyId);
}
