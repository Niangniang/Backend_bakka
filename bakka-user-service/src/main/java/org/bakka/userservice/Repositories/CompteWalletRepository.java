package org.bakka.userservice.Repositories;

import org.bakka.userservice.Entities.CompteWallet;
import org.bakka.userservice.Entities.Wallet;
import org.bakka.userservice.Enums.TypeCompte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CompteWalletRepository extends JpaRepository<CompteWallet, UUID> {
    Boolean existsByNumeroCompte(String numeroCompte);

    Boolean existsByNumeroCompteAndOperateur(String numeroCompte, Wallet operateur);
    List<CompteWallet> getAllByClientBakkaId(UUID id);
}
