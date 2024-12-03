package org.bakka.userservice.Repositories;

import org.bakka.userservice.Entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CompteRepository extends JpaRepository<Compte, UUID> {
}
