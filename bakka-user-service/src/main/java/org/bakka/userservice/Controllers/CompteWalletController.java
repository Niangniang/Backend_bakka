package org.bakka.userservice.Controllers;


import org.bakka.userservice.Dtos.CompteWalletRequestDto;
import org.bakka.userservice.Dtos.CompteWalletResponseDto;

import org.bakka.userservice.Services.CompteWalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/compteWallet")
public class CompteWalletController {

    public final CompteWalletService compteWalletService;

    public CompteWalletController(CompteWalletService compteWalletService) {
        this.compteWalletService = compteWalletService;
    }


    @PostMapping("/add")
    public ResponseEntity<?> addCompteWallet(@RequestBody CompteWalletRequestDto compteWalletRequestDto){
        return compteWalletService.addcompteWallet(compteWalletRequestDto);

    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getCompteWallet(@PathVariable("id") UUID id){
        return compteWalletService.getcompteWallet(id);
    }
    @GetMapping("/all")
    public List<CompteWalletResponseDto> getAllCompteWallet(){
        return compteWalletService.getAllCompteWallet();
    }

    @GetMapping("/getAllClientWalletAccounts/{id}")
    public ResponseEntity<?> getAllClientWalletAccounts(@PathVariable("id") UUID id){
        return compteWalletService.getAllClientWalletAccounts(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?>updateCompteWallet(@PathVariable("id") UUID id,@RequestBody CompteWalletRequestDto compteWalletRequestDto){
        return compteWalletService.updateCompteWallet(id,compteWalletRequestDto);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?>updateCompteWalletFields(@PathVariable("id") UUID id,@RequestBody Map<String,Object> fields){
        return compteWalletService.updateCompteWalletFields(id,fields);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteCompteWallet(@PathVariable("id") UUID id){
        return compteWalletService.deleteCompteWallet(id);
    }
}
