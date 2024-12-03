package org.bakka.userservice.Services;

import org.bakka.userservice.Dtos.BanqueResponseDto;
import org.bakka.userservice.Dtos.CompteBakkaRequestDto;
import org.bakka.userservice.Dtos.CompteBakkaResponseDto;
import org.bakka.userservice.RecodDtos.AddBenificiaireDto;
import org.bakka.userservice.RecodDtos.ChangerPlafondRequestDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CompteBakkaService {
    ResponseEntity<?> addcompteBakka(CompteBakkaRequestDto compteBakkaRequestDto);

    ResponseEntity<?> getcompteBakka(UUID id);

    List<CompteBakkaResponseDto> getAllCompteBakka();

    ResponseEntity<?> updateCompteBakka(UUID id, CompteBakkaRequestDto compteBakkaRequestDto);

    ResponseEntity<?> updateCompteBakkaFields(UUID id, Map<String, Object> fields);

    ResponseEntity<?> deleteCompteBakka(UUID id);

    ResponseEntity<?> chargerCompte(UUID id, String somme);

    ResponseEntity<?> changerPlafond(ChangerPlafondRequestDto changerPlafondRequestDto);

    ResponseEntity<?> addBenificiaire(UUID id, AddBenificiaireDto addBenificiaireDto);

    ResponseEntity<?> retirerBenificiaire(UUID id, AddBenificiaireDto addBenificiaireDto);
}
