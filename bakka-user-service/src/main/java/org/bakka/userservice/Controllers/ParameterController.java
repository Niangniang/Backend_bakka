package org.bakka.userservice.Controllers;

import org.bakka.userservice.Dtos.ParameterRequestDto;
import org.bakka.userservice.Dtos.ParameterResponseDto;
import org.bakka.userservice.Services.ParameterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/parameter")
public class ParameterController {
    public final ParameterService parameterService;

    public ParameterController(ParameterService parameterService) {
        this.parameterService = parameterService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addParameter(@RequestBody ParameterRequestDto parameterRequestDto){
        return parameterService.addParameter(parameterRequestDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getParameter(@PathVariable("id") UUID id){
        return parameterService.getParameter(id);
    }
    @GetMapping("/all")
    public List<ParameterResponseDto> getAllParameters(){
        return parameterService.getAllParameters();
    }
    @GetMapping("/getAllPlafonds")
    public List<ParameterResponseDto>getAllplafonds(){
        return parameterService.getAllPlafonds();
    }
    @PutMapping("/{id}")
    public ResponseEntity<?>updateParameter(@PathVariable("id") UUID id,@RequestBody ParameterRequestDto parameterRequestDto){
        return parameterService.updateParameter(id,parameterRequestDto);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?>updateParameterFields(@PathVariable("id") UUID id,@RequestBody Map<String,Object> fields){
        return parameterService.updateParameterFields(id,fields);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteParameter(@PathVariable("id") UUID id){
        return parameterService.deleteParameter(id);
    }


}
