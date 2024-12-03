package org.bakka.userservice.Mappers;


import org.bakka.userservice.Dtos.ProfilRequestDto;
import org.bakka.userservice.Dtos.ProfilResponseDto;
import org.bakka.userservice.Entities.Profil;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfilMapper {

    ProfilResponseDto profilToProfilResponseDto(Profil profil);

    Profil profilRequestDtoToProfil(ProfilRequestDto profilRequestDto);
}
