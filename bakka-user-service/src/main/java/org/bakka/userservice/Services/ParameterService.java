package org.bakka.userservice.Services;

import org.bakka.userservice.Dtos.ParameterRequestDto;
import org.bakka.userservice.Dtos.ParameterResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ParameterService {
    ResponseEntity<?> addParameter(ParameterRequestDto parameterRequestDto);

    List<ParameterResponseDto> getAllParameters();

    ResponseEntity<?> getParameter(UUID id);

    ResponseEntity<?> updateParameter(UUID id, ParameterRequestDto parameterRequestDto);

    ResponseEntity<?> updateParameterFields(UUID id, Map<String, Object> fields);

    ResponseEntity<?> deleteParameter(UUID id);

    List<ParameterResponseDto> getAllPlafonds();
}
