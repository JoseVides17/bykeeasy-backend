package com.bykeeasy.infrastructure.adapter.out.external;

import com.bykeeasy.domain.model.Coordinate;
import com.bykeeasy.application.port.out.MapServicePort;
import org.springframework.stereotype.Component;

@Component
public class HaversineMapAdapter implements MapServicePort {

    private static final int EARTH_RADIUS = 6371; // km

    @Override
    public double calculateDistance(Coordinate origin, Coordinate destination) {
        double dLat = Math.toRadians(destination.getLatitude() - origin.getLatitude());
        double dLon = Math.toRadians(destination.getLongitude() - origin.getLongitude());
        
        double lat1 = Math.toRadians(origin.getLatitude());
        double lat2 = Math.toRadians(destination.getLatitude());
        
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        return EARTH_RADIUS * c;
    }

    @Override
    public int estimateTimeInMinutes(Coordinate origin, Coordinate destination) {
        double distance = calculateDistance(origin, destination);
        // Average speed for a bike in city could be around 30 km/h
        double speed = 30.0;
        return (int) ((distance / speed) * 60);
    }
}
