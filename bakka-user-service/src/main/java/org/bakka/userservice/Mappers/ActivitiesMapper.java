package org.bakka.userservice.Mappers;

import org.bakka.userservice.Dtos.ActivitiesRequestDto;
import org.bakka.userservice.Dtos.ActivitiesResponseDto;
import org.bakka.userservice.Entities.Activities;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActivitiesMapper {
    ActivitiesResponseDto activitiesToActivitiesResponseDto(Activities activities);
    Activities activitiesRequestDtoToActivities(ActivitiesRequestDto activitiesRequestDto);
}
