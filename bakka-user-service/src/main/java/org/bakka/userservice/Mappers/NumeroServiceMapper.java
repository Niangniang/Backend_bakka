package org.bakka.userservice.Mappers;

import org.bakka.userservice.Dtos.NumeroServiceRequestDto;
import org.bakka.userservice.Dtos.NumeroServiceResponseDto;
import org.bakka.userservice.Entities.NumeroService;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NumeroServiceMapper {
    NumeroServiceResponseDto numeroServiceToNumeroServiceResponseDto(NumeroService numeroService);
    NumeroService numeroServiceRequestDtoToNumeroService(NumeroServiceRequestDto numeroServiceRequestDto);

}
