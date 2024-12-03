package org.bakka.userservice.Controllers;

import org.bakka.userservice.Dtos.BanqueRequestDto;
import org.bakka.userservice.Dtos.CarteRequestDto;
import org.bakka.userservice.Services.BanqueService;
import org.bakka.userservice.Services.CarteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/carte")
public class CarteControllers {

    public final CarteService carteService;

    public CarteControllers(CarteService carteService) {
        this.carteService = carteService;
    }

    /*
    @PostMapping("/add")
    public ResponseEntity<?> addCarte(@RequestBody CarteRequestDto carteRequestDto){
        return carteService.addCarte(carteRequestDto);
    }
    */

    @GetMapping("/{id}")
    public ResponseEntity<?> getCarte(@PathVariable("id") UUID id){

        return carteService.getCarte(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCarte(@PathVariable("id") UUID id,@RequestBody CarteRequestDto carteRequestDto){
        return carteService.updateCarte(id,carteRequestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCarte(@PathVariable("id") UUID id){
        return carteService.deleteCarte(id);
    }

    /*
    @GetMapping("/{solde}")
    public ResponseEntity<?> getSoldeCarte(@PathVariable("id") UUID id, @RequestBody CarteRequestDto carteRequestDto){
        return carteService.soldeCarte(id,carteRequestDto);
    }
    */

    @PutMapping("/demandeCartePhysique/{id}")
    public ResponseEntity<?> demandeCartePhysique(@PathVariable("id") UUID id,@RequestBody CarteRequestDto carteRequestDto){
        return carteService.demandeCartePhysique(id,carteRequestDto);
    }

}
