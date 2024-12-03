package org.bakka.userservice.Repositories;

import org.bakka.userservice.Entities.Activities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ActivitiesRepository extends JpaRepository<Activities, UUID> {
    boolean existsById(UUID uuid);
    boolean existsActivitiesByAdministrateur_Id(UUID id);

}
