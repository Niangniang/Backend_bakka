package org.bakka.userservice.Repositories;

import org.bakka.userservice.Entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OperationRepository extends JpaRepository<Operation, UUID> {
/*    List<Operation> findOperationsBySourceId(UUID id);
    List<Operation> findOperationByDestinationId(UUID id);*/
@Query("SELECT o FROM Operation o WHERE o.source.id = :compteId OR o.destination.id = :compteId")
List<Operation> findOperationsByCompteId(UUID compteId);
}
