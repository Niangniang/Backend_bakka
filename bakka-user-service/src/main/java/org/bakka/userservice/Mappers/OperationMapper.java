package org.bakka.userservice.Mappers;

import org.bakka.userservice.Dtos.OperationRequestDto;
import org.bakka.userservice.Dtos.OperationResponseDto;
import org.bakka.userservice.Entities.Operation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OperationMapper {
    OperationResponseDto operationToOperationResponseDto(Operation operation);
    Operation operationRequestDtoToOperation(OperationRequestDto operationRequestDto);
}
