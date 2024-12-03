package org.bakka.userservice.Controllers;


import org.bakka.userservice.Dtos.CompteBancaireRequestDto;
import org.bakka.userservice.Dtos.CompteBancaireResponseDto;
import org.bakka.userservice.Services.CompteBancaireService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/compteBancaire")
public class CompteBancaireController {
    public final CompteBancaireService compteBancaireService;

    public CompteBancaireController(CompteBancaireService compteBancaireService) {
        this.compteBancaireService = compteBancaireService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCompteBancaire(@RequestBody CompteBancaireRequestDto compteBancaireRequestDto){
        return compteBancaireService.addCompteBancaire(compteBancaireRequestDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getCompteBancaire(@PathVariable("id") UUID id){
        return compteBancaireService.getCompteBancaire(id);
    }
    @GetMapping("/all")
    public List<CompteBancaireResponseDto> getAllCompteBancaires(){
        return compteBancaireService.getAllCompteBancaires();
    }
    @GetMapping("/getAllClientBankAccounts/{id}")
        public ResponseEntity<?> getAllClientBankAccounts(@PathVariable("id") UUID id){
        return compteBancaireService.getAllClientBankAccounts(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?>updateCompteBancaire(@PathVariable("id") UUID id,@RequestBody CompteBancaireRequestDto compteBancaireRequestDto){
        return compteBancaireService.updateCompteBancaire(id,compteBancaireRequestDto);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?>updateCompteBancaireFields(@PathVariable("id") UUID id,@RequestBody Map<String,Object> fields){
        return compteBancaireService.updateCompteBancaireFields(id,fields);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteCompteBancaire(@PathVariable("id") UUID id){
        return compteBancaireService.deleteCompteBancaire(id);
    }
}
