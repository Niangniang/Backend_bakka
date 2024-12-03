package org.bakka.userservice.Controllers;

import org.bakka.userservice.Dtos.ProfilRequestDto;
import org.bakka.userservice.Dtos.ProfilResponseDto;
import org.bakka.userservice.Services.ProfilService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/profil")
public class ProfilController {

    public final ProfilService profilService;

    private static final Logger logger = LoggerFactory.getLogger(ProfilController.class);



    public ProfilController(ProfilService profilService) {
        this.profilService = profilService;
    }

    @PostMapping("/add")
    public ResponseEntity<?>addProfil(@RequestBody ProfilRequestDto profilRequestDto){
        return profilService.addProfil(profilRequestDto);

    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getProfil(@PathVariable("id") UUID id){
        return profilService.getProfil(id);
    }
    @GetMapping("/all")
    public List<ProfilResponseDto> getAllProfils(){
        return profilService.getAllProfils();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?>updateProfil(@PathVariable("id") UUID id,@RequestBody ProfilRequestDto profilRequestDto){
        return profilService.updateProfil(id,profilRequestDto);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?>updateProfilFields(@PathVariable("id") UUID id,@RequestBody Map<String,Object> fields){
        return profilService.updateProfilFields(id,fields);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteProfil(@PathVariable("id") UUID id){
        return profilService.deleteProfil(id);
    }



}
