package org.bakka.userservice.Mappers;

import org.bakka.userservice.Dtos.BeneficiaireRequestDto;
import org.bakka.userservice.Dtos.BeneficiaireResponseDto;
import org.bakka.userservice.Entities.Beneficiaire;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BeneficiaireMapper {

    BeneficiaireResponseDto beneficiaireToBeneficiaireResponseDto(Beneficiaire beneficiaire);

    Beneficiaire beneficiaireRequestDtoToBeneficiaire(BeneficiaireRequestDto beneficiaireRequestDto);

}
