package org.bakka.userservice.Services;

import org.bakka.userservice.Dtos.NumeroServiceRequestDto;
import org.bakka.userservice.Dtos.NumeroServiceResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface NumeroServiceService {

    ResponseEntity<?> addNumeroService(NumeroServiceRequestDto numeroServiceRequestDto);
    ResponseEntity<?> getNumeroService(UUID uuid);
    List<NumeroServiceResponseDto> getAllNumeroService();
    ResponseEntity<?> updateNumeroService(NumeroServiceRequestDto numeroServiceRequestDto);
    ResponseEntity<?> deleteNumeroService(UUID uuid);
}
