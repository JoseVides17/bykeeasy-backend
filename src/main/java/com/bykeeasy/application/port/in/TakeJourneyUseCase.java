package com.bykeeasy.application.port.in;

public interface TakeJourneyUseCase {
    void takeJourney(String journeyId, String driverId);
    void completeJourney(String journeyId);
    void cancelJourney(String journeyId);
}
