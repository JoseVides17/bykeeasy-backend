package com.bykeeasy.application.port.in;

import com.bykeeasy.infrastructure.adapter.in.web.RecentDestinationDto;
import java.util.List;

public interface DestinationUseCase {
    List<RecentDestinationDto> getRecentDestinations(String passengerId);
}
