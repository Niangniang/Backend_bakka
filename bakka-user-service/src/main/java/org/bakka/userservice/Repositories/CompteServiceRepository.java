package org.bakka.userservice.Repositories;

import org.bakka.userservice.Entities.CompteService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CompteServiceRepository extends JpaRepository<CompteService, UUID> {
    boolean existsByNomFacturier(String nomFacturier);
}
