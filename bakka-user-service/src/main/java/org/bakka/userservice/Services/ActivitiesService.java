package org.bakka.userservice.Services;

import org.bakka.userservice.Dtos.ActivitiesRequestDto;
import org.bakka.userservice.Dtos.ActivitiesResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ActivitiesService {

    ResponseEntity<?> addActivities(ActivitiesRequestDto activitiesRequestDto);
    ResponseEntity<?> updateActivities(ActivitiesRequestDto activitiesRequestDto);
    ResponseEntity<?> getActivities(UUID uuid);
    ResponseEntity<?> deleteActivities(UUID uuid);
    List<ActivitiesResponseDto> getAllActivities();
}
