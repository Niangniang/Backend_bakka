package org.bakka.userservice.Services;

import org.bakka.userservice.Dtos.ProfilRequestDto;
import org.bakka.userservice.Dtos.ProfilResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProfilService {


    ResponseEntity<?> addProfil(ProfilRequestDto profilRequestDto);

    ResponseEntity<?> deleteProfil(UUID id);
    List<ProfilResponseDto>getAllProfils();

    ResponseEntity<?> getProfil(UUID id);

    ResponseEntity<?> updateProfil(UUID id, ProfilRequestDto profilRequestDto);

    ResponseEntity<?> updateProfilFields(UUID id, Map<String, Object> fields);
}
