package org.bakka.userservice.Mappers;

import org.bakka.userservice.Dtos.OtpValidationRequestDto;
import org.bakka.userservice.Dtos.OtpValidationResponseDto;
import org.bakka.userservice.Entities.OtpValidation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OtpValidationMapper {
    OtpValidation otpValidationRequestDtoToOtpValidation(OtpValidationRequestDto otpValidationRequestDto);


}
