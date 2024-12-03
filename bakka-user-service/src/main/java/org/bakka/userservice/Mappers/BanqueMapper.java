package org.bakka.userservice.Mappers;

import org.bakka.userservice.Dtos.BanqueRequestDto;
import org.bakka.userservice.Dtos.BanqueResponseDto;
import org.bakka.userservice.Entities.Banque;
import org.mapstruct.Mapper;
import org.springframework.web.multipart.MultipartFile;

@Mapper(componentModel = "spring")
public interface BanqueMapper {
    String map(MultipartFile value);
    BanqueResponseDto banqueToBanqueResponseDto(Banque banque);
    Banque banqueRequestDtoToBanque(BanqueRequestDto banqueRequestDto);
}
