package org.bakka.userservice.Controllers;
import org.bakka.userservice.Dtos.OperationRequestDto;
import org.bakka.userservice.Dtos.OperationResponseDto;
import org.bakka.userservice.Services.OperationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/operation")
public class OperationController {
    public final OperationService operationService;

    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addOperation(@RequestBody OperationRequestDto operationRequestDto){
        return operationService.addOperation(operationRequestDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOperation(@PathVariable("id") UUID id){
        return operationService.getOperation(id);
    }
    @GetMapping("/all")
    public List<OperationResponseDto> getAllOperations(){
        return operationService.getAllOperations();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?>updateOperation(@PathVariable("id") UUID id,@RequestBody OperationRequestDto operationRequestDto){
        return operationService.updateOperation(id,operationRequestDto);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?>updateOperationFields(@PathVariable("id") UUID id,@RequestBody Map<String,Object> fields){
        return operationService.updateOperationFields(id,fields);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteOperation(@PathVariable("id") UUID id){
        return operationService.deleteOperation(id);
    }
    @GetMapping("/historiqueCompte/{id}")
    public List<OperationResponseDto>getAllOperationsOfCompte(@PathVariable("id") UUID id){
        return operationService.historiqueCompte(id);
    }

}
