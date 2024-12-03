package org.bakka.userservice.Mappers;


import org.bakka.userservice.Dtos.CompteBakkaRequestDto;
import org.bakka.userservice.Dtos.CompteBakkaResponseDto;
import org.bakka.userservice.Entities.CompteBakka;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompteBakkaMapper {
    CompteBakkaResponseDto compteBakkaToCompteBakkaResponseDto(CompteBakka compteBakka);
    CompteBakka compteBakkaRequestDtoToCompteBakka(CompteBakkaRequestDto compteBakkaRequestDto);
}
