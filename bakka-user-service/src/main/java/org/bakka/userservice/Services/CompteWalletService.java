package org.bakka.userservice.Services;

import org.bakka.userservice.Dtos.BanqueResponseDto;
import org.bakka.userservice.Dtos.CompteWalletRequestDto;
import org.bakka.userservice.Dtos.CompteWalletResponseDto;
import org.bakka.userservice.Entities.CompteWallet;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CompteWalletService {
    ResponseEntity<?> addcompteWallet(CompteWalletRequestDto compteWalletRequestDto);

    ResponseEntity<?> getcompteWallet(UUID id);

    List<CompteWalletResponseDto> getAllCompteWallet();

    ResponseEntity<?> updateCompteWallet(UUID id, CompteWalletRequestDto compteWalletRequestDto);

    ResponseEntity<?> updateCompteWalletFields(UUID id, Map<String, Object> fields);

    ResponseEntity<?> deleteCompteWallet(UUID id);

    ResponseEntity<?> getAllClientWalletAccounts(UUID id);
}
