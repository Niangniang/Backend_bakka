package org.bakka.userservice.Mappers;

import org.bakka.userservice.Dtos.CompteBancaireRequestDto;
import org.bakka.userservice.Dtos.CompteBancaireResponseDto;
import org.bakka.userservice.Entities.CompteBancaire;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompteBancaireMapper {
    CompteBancaireResponseDto compteBancaireToCompteBancaireResponseDto( CompteBancaire compteBancaire);
    CompteBancaire compteBancaireRequestDtoToCompteBancaire(CompteBancaireRequestDto compteBancaireRequestDto);
}
