package org.bakka.userservice.Repositories;

import org.bakka.userservice.Entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    Boolean existsByIntitule(String intitule);
}
