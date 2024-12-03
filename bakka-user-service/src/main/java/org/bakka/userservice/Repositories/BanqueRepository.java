package org.bakka.userservice.Repositories;

import org.bakka.userservice.Entities.Banque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BanqueRepository extends JpaRepository<Banque, UUID> {
    Boolean existsBanqueByIntitule(String intitule);
}
