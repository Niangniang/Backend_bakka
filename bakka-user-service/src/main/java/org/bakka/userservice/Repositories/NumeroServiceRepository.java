package org.bakka.userservice.Repositories;

import org.bakka.userservice.Entities.NumeroService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NumeroServiceRepository extends JpaRepository<NumeroService, UUID> {
    boolean existsNumeroServiceById(UUID id);
}
