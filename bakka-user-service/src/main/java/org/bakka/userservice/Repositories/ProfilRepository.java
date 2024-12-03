package org.bakka.userservice.Repositories;

import org.bakka.userservice.Entities.Profil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfilRepository  extends JpaRepository<Profil, UUID> {
Boolean existsProfilByLibelle(String libelle);

    Profil findById(Profil profil);

    Optional<Profil> findByLibelle(String libelle);


}
