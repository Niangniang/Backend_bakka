package org.bakka.userservice.Mappers;


import org.bakka.userservice.Dtos.CompteWalletRequestDto;
import org.bakka.userservice.Dtos.CompteWalletResponseDto;
import org.bakka.userservice.Entities.CompteWallet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompteWalletMapper {


    CompteWalletResponseDto compteWalletToComteWalletResponseDto(CompteWallet compteWallet);
    CompteWallet compteWalletRequestDtoToCompteWallet(CompteWalletRequestDto compteWalletRequestDto);
}
