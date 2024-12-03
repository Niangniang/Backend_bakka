package org.bakka.userservice.Services;

import org.bakka.userservice.Dtos.CompteServiceRequestDto;
import org.bakka.userservice.Dtos.CompteServiceResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface CompteServiceService {

    ResponseEntity<?> addCompteService(CompteServiceRequestDto compteServiceRequestDto);
    ResponseEntity<?> getCompteService(UUID uuid);
    List<CompteServiceResponseDto> getAllCompteService();
    ResponseEntity<?> updateCompteService(CompteServiceRequestDto compteServiceRequestDto);
    ResponseEntity<?> deleteCompteService(UUID uuid);

}
