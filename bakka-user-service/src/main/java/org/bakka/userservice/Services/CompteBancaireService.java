package org.bakka.userservice.Services;

import org.bakka.userservice.Dtos.CompteBancaireRequestDto;
import org.bakka.userservice.Dtos.CompteBancaireResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.UUID;


public interface CompteBancaireService {
    ResponseEntity<?> addCompteBancaire(CompteBancaireRequestDto compteBancaireRequestDto);

    ResponseEntity<?> getCompteBancaire(UUID id);

    List<CompteBancaireResponseDto> getAllCompteBancaires();

    ResponseEntity<?> updateCompteBancaire(UUID id, CompteBancaireRequestDto compteBancaireRequestDto);

    ResponseEntity<?> updateCompteBancaireFields(UUID id, Map<String, Object> fields);

    ResponseEntity<?> deleteCompteBancaire(UUID id);

    ResponseEntity<?> getAllClientBankAccounts(UUID id);
}
