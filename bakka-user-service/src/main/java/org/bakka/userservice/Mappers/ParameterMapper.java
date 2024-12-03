package org.bakka.userservice.Mappers;

import org.bakka.userservice.Dtos.ParameterRequestDto;
import org.bakka.userservice.Dtos.ParameterResponseDto;
import org.bakka.userservice.Entities.Parameter;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface ParameterMapper {
    ParameterResponseDto parameterToParameterResponseDto(Parameter parameter);
    Parameter parameterRequestDtoToParameter(ParameterRequestDto parameterRequestDto);
}
