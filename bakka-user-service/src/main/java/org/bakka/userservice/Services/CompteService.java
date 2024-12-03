package org.bakka.userservice.Services;

import org.bakka.userservice.Dtos.CompteRequestDto;
import org.bakka.userservice.Dtos.CompteResponseDto;
import org.bakka.userservice.Dtos.TransferRequestDto;
import org.bakka.userservice.Entities.Compte;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CompteService {
    ResponseEntity<?> transferAccountToAccount(TransferRequestDto transferRequestDto);

    ResponseEntity<?> addCompte(CompteRequestDto compteRequestDto);

    ResponseEntity<?> getCompte(UUID id);

    List<CompteResponseDto> getAllComptes();

    ResponseEntity<?> updateCompte(UUID id, CompteRequestDto compteRequestDto);

    ResponseEntity<?> updateCompteFields(UUID id, Map<String, Object> fields);

    ResponseEntity<?> deleteCompte(UUID id);

    ResponseEntity<?> getAllAccountsExceptBakka(UUID id);
}
