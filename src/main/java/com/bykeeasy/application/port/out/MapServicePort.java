package com.bykeeasy.application.port.out;

import com.bykeeasy.domain.model.Coordinate;

public interface MapServicePort {
    double calculateDistance(Coordinate origin, Coordinate destination);
    int estimateTimeInMinutes(Coordinate origin, Coordinate destination);
}
