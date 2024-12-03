package org.bakka.userservice.Controllers;


import org.bakka.userservice.Dtos.CompteServiceRequestDto;
import org.bakka.userservice.Dtos.CompteServiceResponseDto;
import org.bakka.userservice.Services.CompteServiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/compteService")
public class CompteServiceController {
    public final CompteServiceService compteServiceService;

    public CompteServiceController(CompteServiceService compteServiceService) {
        this.compteServiceService = compteServiceService;
    }
    @PostMapping("/add")
    public ResponseEntity<?> addCompteService(@RequestBody CompteServiceRequestDto compteServiceRequestDto){
        return compteServiceService.addCompteService(compteServiceRequestDto);
    }
    @PutMapping("/update")
    public ResponseEntity<?>updateCompteService(@RequestBody CompteServiceRequestDto compteServiceRequestDto){
        return compteServiceService.updateCompteService(compteServiceRequestDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompteService(@PathVariable("id") UUID id){
        return compteServiceService.deleteCompteService(id);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getCompteService(@PathVariable("id") UUID id){
        return compteServiceService.getCompteService(id);
    }
    @GetMapping("/all")
    public List<CompteServiceResponseDto> getAllCompteService(){
        return compteServiceService.getAllCompteService();
    }
}
