package org.bakka.userservice.Controllers;

import org.bakka.userservice.Dtos.NumeroServiceRequestDto;
import org.bakka.userservice.Dtos.NumeroServiceResponseDto;
import org.bakka.userservice.Services.NumeroServiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/NumeroService")
public class NumeroServiceController {
    public final NumeroServiceService numeroServiceService;

    public NumeroServiceController(NumeroServiceService numeroServiceService) {
        this.numeroServiceService = numeroServiceService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNumeroService(@RequestBody NumeroServiceRequestDto numeroServiceRequestDto){
        return numeroServiceService.addNumeroService(numeroServiceRequestDto);
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateNumeroService(@RequestBody NumeroServiceRequestDto numeroServiceRequestDto){
        return numeroServiceService.updateNumeroService(numeroServiceRequestDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNumeroService(@PathVariable("id") UUID id){
        return numeroServiceService.deleteNumeroService(id);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getNumeroService(@PathVariable("id") UUID id){
        return numeroServiceService.getNumeroService(id);
    }
    @GetMapping("/all")
    public List<NumeroServiceResponseDto> getAllNumeroService(){
        return numeroServiceService.getAllNumeroService();
    }
}
