package org.bakka.userservice.Services;

import org.bakka.userservice.Dtos.OperationRequestDto;
import org.bakka.userservice.Dtos.OperationResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface OperationService {
    ResponseEntity<?> addOperation(OperationRequestDto operationRequestDto);

    ResponseEntity<?> getOperation(UUID id);

    List<OperationResponseDto> getAllOperations();

    ResponseEntity<?> updateOperation(UUID id, OperationRequestDto operationRequestDto);

    ResponseEntity<?> updateOperationFields(UUID id, Map<String, Object> fields);

    ResponseEntity<?> deleteOperation(UUID id);

    List<OperationResponseDto> historiqueCompte(UUID id);
}
