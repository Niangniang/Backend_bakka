package org.bakka.userservice.Controllers;


import org.bakka.userservice.Dtos.BanqueResponseDto;
import org.bakka.userservice.Dtos.CompteBakkaRequestDto;
import org.bakka.userservice.Dtos.CompteBakkaResponseDto;
import org.bakka.userservice.RecodDtos.AddBenificiaireDto;
import org.bakka.userservice.RecodDtos.ChangerPlafondRequestDto;
import org.bakka.userservice.Services.CompteBakkaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/compteBakka")
public class CompteBakkaController {
   public final CompteBakkaService compteBakkaService;

    public CompteBakkaController(CompteBakkaService compteBakkaService) {
        this.compteBakkaService = compteBakkaService;
    }

    @PatchMapping("/charger/{id}")
    public ResponseEntity<?>chargerCompte(@PathVariable("id") UUID id, @RequestBody String somme){
        return compteBakkaService.chargerCompte(id,somme);
    }
    @PatchMapping("/addBenificiaire/{id}")
    public ResponseEntity<?> addBenificiaire (@PathVariable("id") UUID id, @RequestBody AddBenificiaireDto addBenificiaireDto){
        return compteBakkaService.addBenificiaire(id, addBenificiaireDto);
    }

    @PatchMapping("/retirerBenificiaire/{id}")
    public ResponseEntity<?> retirerBenificiaire (@PathVariable("id") UUID id, @RequestBody AddBenificiaireDto addBenificiaireDto){
        return compteBakkaService.retirerBenificiaire(id, addBenificiaireDto);
    }


    @PostMapping("/add")
    public ResponseEntity<?> addCompteBakka(@RequestBody CompteBakkaRequestDto compteBakkaRequestDto){
        return compteBakkaService.addcompteBakka(compteBakkaRequestDto);
    }
    @PostMapping("/changerPlafond")
    public ResponseEntity<?>changerPlafond(@RequestBody ChangerPlafondRequestDto changerPlafondRequestDto){
        return compteBakkaService.changerPlafond(changerPlafondRequestDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getCompteBakka(@PathVariable("id") UUID id){
        return compteBakkaService.getcompteBakka(id);
    }
    @GetMapping("/all")
    public List<CompteBakkaResponseDto> getAllCompteBakka(){
        return compteBakkaService.getAllCompteBakka();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?>updateCompteBakka(@PathVariable("id") UUID id,@RequestBody CompteBakkaRequestDto compteBakkaRequestDto){
        return compteBakkaService.updateCompteBakka(id,compteBakkaRequestDto);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?>updateCompteBakkaFields(@PathVariable("id") UUID id,@RequestBody Map<String,Object> fields){
        return compteBakkaService.updateCompteBakkaFields(id,fields);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteCompteBakka(@PathVariable("id") UUID id){
        return compteBakkaService.deleteCompteBakka(id);
    }
}
