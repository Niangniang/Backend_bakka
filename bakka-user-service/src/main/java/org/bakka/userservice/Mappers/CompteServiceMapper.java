package org.bakka.userservice.Mappers;

import org.bakka.userservice.Dtos.CompteServiceRequestDto;
import org.bakka.userservice.Dtos.CompteServiceResponseDto;
import org.bakka.userservice.Entities.CompteService;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompteServiceMapper {
    CompteServiceResponseDto compteServiceToCompteServiceResponseDto(CompteService compteService);
    CompteService compteServiceRequestDtoToCompteService(CompteServiceRequestDto compteServiceRequestDto);

}
