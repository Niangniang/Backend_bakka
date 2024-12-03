package org.bakka.userservice.Mappers;


import org.bakka.userservice.Dtos.AdministrateurRequestDto;
import org.bakka.userservice.Dtos.AdministrateurResponseDto;
import org.bakka.userservice.Entities.Administrateur;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdministrateurMapper {
    AdministrateurResponseDto administrateurToAdministrateurResponseDto(Administrateur administrateur);

    Administrateur administrateurRequestDtoToAdministrateur(AdministrateurRequestDto administrateurRequestDto);


}
