package org.bakka.userservice.Mappers;

import org.bakka.userservice.Dtos.BanqueRequestDto;
import org.bakka.userservice.Dtos.BanqueResponseDto;
import org.bakka.userservice.Dtos.CarteRequestDto;
import org.bakka.userservice.Dtos.CarteResponseDto;
import org.bakka.userservice.Entities.Banque;
import org.bakka.userservice.Entities.Carte;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarteMapper {

    CarteResponseDto carteToCarteResponseDto(Carte carte);
    Carte carteRequestDtoToCarte(CarteRequestDto carteRequestDto);

}
