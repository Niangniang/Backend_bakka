package org.bakka.userservice.Repositories;

import org.bakka.userservice.Entities.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, UUID> {
    Boolean existsByLibelle(String libelle);
    Set<Parameter> findParameterByLibelle(String libelle);
    Parameter findParameterByLibelleContains(String word);
}
