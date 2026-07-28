package com.bykeeasy.infrastructure.adapter.in.websocket;

import com.bykeeasy.domain.model.Coordinate;
import com.bykeeasy.application.port.in.UpdateLocationDriverUseCase;
import lombok.Data;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class LocationWebSocketController {

    private final UpdateLocationDriverUseCase updateLocationDriverUseCase;

    public LocationWebSocketController(UpdateLocationDriverUseCase updateLocationDriverUseCase) {
        this.updateLocationDriverUseCase = updateLocationDriverUseCase;
    }

    @MessageMapping("/driver/{driverId}/location")
    public void updateLocation(@DestinationVariable String driverId, LocationUpdateMessage message) {
        updateLocationDriverUseCase.updateLocation(driverId, new Coordinate(message.getLat(), message.getLon()));
    }

    @Data
    public static class LocationUpdateMessage {
        private double lat;
        private double lon;
    }
}
