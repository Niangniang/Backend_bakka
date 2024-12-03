package org.bakka.userservice.Services;


import org.bakka.userservice.Dtos.BanqueRequestDto;
import org.bakka.userservice.Dtos.CarteRequestDto;
import org.bakka.userservice.Entities.CompteBakka;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface CarteService {

    //ResponseEntity<?> addCarte(CarteRequestDto carteRequestDto);
    ResponseEntity<?> getCarte(UUID id);
    ResponseEntity<?> updateCarte(UUID id, CarteRequestDto carteRequestDto);
    ResponseEntity<?> deleteCarte(UUID id);

    //ResponseEntity<?> soldeCarte(UUID id, CarteRequestDto carteRequestDto);

    ResponseEntity<?> demandeCartePhysique(UUID id,CarteRequestDto carteRequestDto);

}
