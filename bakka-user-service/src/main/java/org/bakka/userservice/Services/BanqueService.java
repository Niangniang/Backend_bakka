package org.bakka.userservice.Services;

import org.bakka.userservice.Dtos.BanqueRequestDto;
import org.bakka.userservice.Dtos.BanqueResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface BanqueService {
    ResponseEntity<?> addBanque(BanqueRequestDto banqueRequestDto);

    ResponseEntity<?> getBanque(UUID id);

    List<BanqueResponseDto> getAllBanques();

    ResponseEntity<?> updateBanque(UUID id, BanqueRequestDto banqueRequestDto);

    ResponseEntity<?> updateBanqueFields(UUID id, Map<String, Object> fields);

    ResponseEntity<?> deleteBanque(UUID id);
}
