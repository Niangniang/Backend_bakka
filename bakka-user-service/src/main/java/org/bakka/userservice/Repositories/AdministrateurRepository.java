package org.bakka.userservice.Repositories;

import org.bakka.userservice.Entities.Administrateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdministrateurRepository extends JpaRepository<Administrateur, UUID> {


    Optional<Administrateur> findByUsername(String userName);

    boolean existsByUsername(String userName);


}
