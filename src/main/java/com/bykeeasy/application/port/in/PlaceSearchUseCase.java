package com.bykeeasy.application.port.in;

import com.bykeeasy.infrastructure.adapter.in.web.PlaceDto;
import java.util.List;

public interface PlaceSearchUseCase {
    List<PlaceDto> searchPlaces(String query);
}
