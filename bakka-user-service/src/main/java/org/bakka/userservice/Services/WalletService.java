package org.bakka.userservice.Services;

import org.bakka.userservice.Dtos.WalletRequestDto;
import org.bakka.userservice.Dtos.WalletResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface WalletService {
    ResponseEntity<?> addWallet(WalletRequestDto walletRequestDto);

    ResponseEntity<?> getWallet(UUID id);

    List<WalletResponseDto> getAllWallet();

    ResponseEntity<?> updateWallet(UUID id, WalletRequestDto walletRequestDto);

    ResponseEntity<?> updateWalletFields(UUID id, Map<String, Object> fields);

    ResponseEntity<?> deleteWallet(UUID id);
}
