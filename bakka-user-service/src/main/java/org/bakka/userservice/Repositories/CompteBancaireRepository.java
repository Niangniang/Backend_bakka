package org.bakka.userservice.Repositories;

import org.bakka.userservice.Entities.CompteBancaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CompteBancaireRepository extends JpaRepository<CompteBancaire, UUID> {
    Boolean existsByNumeroCompte(String numeroCompte);
    List<CompteBancaire> getAllByClientBakkaId(UUID id);
}
