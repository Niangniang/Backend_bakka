package org.bakka.userservice.Mappers;

import org.bakka.userservice.Dtos.ClientBakkaRequestDto;
import org.bakka.userservice.Dtos.ClientBakkaResponseDto;
import org.bakka.userservice.Entities.ClientBakka;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientBakkaMapper {
    ClientBakkaResponseDto clientBakkaToClientBakkaResponseDto(ClientBakka clientBakka);
    ClientBakka clientBakkaRequestDtoClientBakka(ClientBakkaRequestDto clientBakkaRequestDto);
}
