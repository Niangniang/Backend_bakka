package org.bakka.userservice.Repositories;

import org.bakka.userservice.Entities.Banque;
import org.bakka.userservice.Entities.Carte;
import org.bakka.userservice.Entities.CompteBakka;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CarteRepository extends JpaRepository<Carte, UUID> {

    //Carte findCarteByCompteBakka(CompteBakka compteBakka);

    Carte findCarteByNumero(String numero);

    Carte findCarteByReference(String reference);

    Boolean existsByCvv(String cvv);

    //Boolean existsCarteByCompteBakka(CompteBakka compteBakka);

    Boolean existsByNumero(String numero);
    Boolean existsByReference(String reference);

    //Optional<Carte> findByCompteBakka(CompteBakka compte);


}
