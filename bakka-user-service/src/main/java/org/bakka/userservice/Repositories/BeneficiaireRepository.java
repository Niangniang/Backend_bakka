package org.bakka.userservice.Repositories;


import org.bakka.userservice.Entities.Beneficiaire;
import org.bakka.userservice.Entities.CompteBakka;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BeneficiaireRepository  extends JpaRepository<Beneficiaire , UUID> {

    Optional<Beneficiaire> findByNumeroCompte(String numeroCompte);

    Optional<Beneficiaire> findByTelephone(String telephone);;
}
