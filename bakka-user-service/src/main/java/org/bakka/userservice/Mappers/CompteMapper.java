package org.bakka.userservice.Mappers;

import org.bakka.userservice.Dtos.CompteRequestDto;
import org.bakka.userservice.Dtos.CompteResponseDto;
import org.bakka.userservice.Entities.Compte;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompteMapper {
    CompteResponseDto compteToCompteResponseDto(Compte compte);
    Compte compteRequestDtoToCompte(CompteRequestDto compteRequestDto);
}
