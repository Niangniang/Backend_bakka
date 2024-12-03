package org.bakka.userservice.Mappers;

import org.bakka.userservice.Dtos.WalletRequestDto;
import org.bakka.userservice.Dtos.WalletResponseDto;
import org.bakka.userservice.Entities.Wallet;
import org.mapstruct.Mapper;
import org.springframework.web.multipart.MultipartFile;

@Mapper(componentModel = "spring")
public interface WalletMapper {
    String map(MultipartFile value);
    WalletResponseDto walletToWalletResponseDto(Wallet wallet);
    Wallet walletRequestDtoToWallet(WalletRequestDto walletRequestDto);
}
