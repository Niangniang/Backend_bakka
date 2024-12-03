package org.bakka.userservice.Controllers;

import org.bakka.userservice.Dtos.CompteRequestDto;
import org.bakka.userservice.Dtos.CompteResponseDto;
import org.bakka.userservice.Dtos.TransferRequestDto;
import org.bakka.userservice.Entities.Compte;
import org.bakka.userservice.Services.CompteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/compte")
public class CompteController {
public final CompteService compteService;


    public CompteController(CompteService compteService) {
        this.compteService = compteService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCompte(@RequestBody CompteRequestDto compteRequestDto){
        return compteService.addCompte(compteRequestDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getCompte(@PathVariable("id") UUID id){
        return compteService.getCompte(id);
    }
    @GetMapping("/all")
    public List<CompteResponseDto> getAllComptes(){
        return compteService.getAllComptes();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?>updateCompte(@PathVariable("id") UUID id,@RequestBody CompteRequestDto compteRequestDto){
        return compteService.updateCompte(id,compteRequestDto);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?>updateCompteFields(@PathVariable("id") UUID id,@RequestBody Map<String,Object> fields){
        return compteService.updateCompteFields(id,fields);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteCompte(@PathVariable("id") UUID id){
        return compteService.deleteCompte(id);
    }
    @PostMapping("/transfert")
    public ResponseEntity<?> tranferAccountToAccount(@RequestBody TransferRequestDto transferRequestDto){
        return compteService.transferAccountToAccount(transferRequestDto);
    }
    @GetMapping("/getAllAccountsExceptBakka/{id}")
    public ResponseEntity<?> getAllAccountsExceptBakka(@PathVariable("id") UUID id){
        return compteService.getAllAccountsExceptBakka(id);
    }
}
