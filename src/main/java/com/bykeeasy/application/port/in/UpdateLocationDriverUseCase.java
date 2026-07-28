package com.bykeeasy.application.port.in;

import com.bykeeasy.domain.model.Coordinate;

public interface UpdateLocationDriverUseCase {
    void updateLocation(String driverId, Coordinate location);
}
