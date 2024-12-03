package org.bakka.userservice.Controllers;

import org.bakka.userservice.Dtos.BanqueRequestDto;
import org.bakka.userservice.Dtos.BanqueResponseDto;
import org.bakka.userservice.Services.BanqueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/banque")
public class BanqueController {
    public final BanqueService banqueService;


    public BanqueController(BanqueService banqueService) {
        this.banqueService = banqueService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBanque(@ModelAttribute BanqueRequestDto banqueRequestDto){
        return banqueService.addBanque(banqueRequestDto);

    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getBanque(@PathVariable("id") UUID id){
        return banqueService.getBanque(id);
    }
    @GetMapping("/all")
    public List<BanqueResponseDto> getAllBanques(){
        return banqueService.getAllBanques();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?>updateBanque(@PathVariable("id") UUID id,@RequestBody BanqueRequestDto banqueRequestDto){
        return banqueService.updateBanque(id,banqueRequestDto);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?>updateBanqueFields(@PathVariable("id") UUID id,@RequestBody Map<String,Object> fields){
        return banqueService.updateBanqueFields(id,fields);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteBanque(@PathVariable("id") UUID id){
        return banqueService.deleteBanque(id);
    }
}
