package org.bakka.userservice.Controllers;

import org.bakka.userservice.Dtos.BanqueResponseDto;
import org.bakka.userservice.Dtos.CompteWalletRequestDto;
import org.bakka.userservice.Dtos.WalletRequestDto;
import org.bakka.userservice.Dtos.WalletResponseDto;
import org.bakka.userservice.Services.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    public final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }


    @PostMapping("/add")
    public ResponseEntity<?> addWallet(@ModelAttribute WalletRequestDto walletRequestDto){
        return walletService.addWallet(walletRequestDto);

    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getWallet(@PathVariable("id") UUID id){
        return walletService.getWallet(id);
    }
    @GetMapping("/all")
    public List<WalletResponseDto> getAllWallet(){
        return walletService.getAllWallet();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?>updateWallet(@PathVariable("id") UUID id,@RequestBody WalletRequestDto walletRequestDto){
        return walletService.updateWallet(id,walletRequestDto);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?>updateWalletFields(@PathVariable("id") UUID id,@RequestBody Map<String,Object> fields){
        return walletService.updateWalletFields(id,fields);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteCompteWallet(@PathVariable("id") UUID id){
        return walletService.deleteWallet(id);
    }
}
