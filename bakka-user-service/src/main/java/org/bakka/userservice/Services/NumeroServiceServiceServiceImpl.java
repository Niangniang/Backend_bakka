package org.bakka.userservice.Services;

import jakarta.transaction.Transactional;
import org.bakka.userservice.Dtos.NumeroServiceRequestDto;
import org.bakka.userservice.Dtos.NumeroServiceResponseDto;
import org.bakka.userservice.Entities.NumeroService;
import org.bakka.userservice.Mappers.NumeroServiceMapper;
import org.bakka.userservice.Repositories.NumeroServiceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class NumeroServiceServiceServiceImpl implements NumeroServiceService {

    private final NumeroServiceMapper numeroServiceMapper;
    private final NumeroServiceRepository numeroServiceRepository;

    Map<String, Object> ns = new HashMap<>();

    public NumeroServiceServiceServiceImpl(NumeroServiceMapper numeroServiceMapper, NumeroServiceRepository numeroServiceRepository) {
        this.numeroServiceMapper = numeroServiceMapper;
        this.numeroServiceRepository = numeroServiceRepository;
    }

    @Override
    public ResponseEntity<?> addNumeroService(NumeroServiceRequestDto numeroServiceRequestDto) {
        try {
            NumeroService numeroService = numeroServiceMapper.numeroServiceRequestDtoToNumeroService(numeroServiceRequestDto);
            NumeroService save = numeroServiceRepository.save(numeroService);
            NumeroServiceResponseDto numeroServiceResponseDto = numeroServiceMapper.numeroServiceToNumeroServiceResponseDto(save);
            ns.put("Message","Ce numéro de service a été ajouté avec succès");
            ns.put("data", numeroServiceResponseDto);
            return  new ResponseEntity<Object>(ns, HttpStatus.CREATED);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> getNumeroService(UUID uuid) {
        try {
            if(numeroServiceRepository.existsById(uuid)){
                NumeroService numeroService = numeroServiceRepository.findById(uuid).get();
                NumeroServiceResponseDto numeroServiceResponseDto = numeroServiceMapper.numeroServiceToNumeroServiceResponseDto(numeroService);
                return ResponseEntity.ok(numeroServiceResponseDto);
            }else {
                ns.put("message", "Ce numero de service n'existe pas !");
                return new ResponseEntity<Object>(ns, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<NumeroServiceResponseDto> getAllNumeroService() {
        try {
            List<NumeroService> numeroServiceList = numeroServiceRepository.findAll();
            return numeroServiceList.stream().map(numeroServiceMapper::numeroServiceToNumeroServiceResponseDto).toList();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> updateNumeroService(NumeroServiceRequestDto numeroServiceRequestDto) {
        try {
            if (!numeroServiceRepository.existsNumeroServiceById(numeroServiceRequestDto.getId())){
                ns.put("message","Ce numéro de service n'existe pas");
                return new ResponseEntity<Object>(ns, HttpStatus.BAD_REQUEST);
            }else {
                NumeroService numeroService = numeroServiceMapper.numeroServiceRequestDtoToNumeroService(numeroServiceRequestDto);
                NumeroService update = numeroServiceRepository.save(numeroService);
                NumeroServiceResponseDto numeroServiceResponseDto = numeroServiceMapper.numeroServiceToNumeroServiceResponseDto(update);
                ns.put("Message", "Ce numéro de service a été bien modifié");
                ns.put("data", numeroServiceResponseDto);
                return new ResponseEntity<Object>(ns, HttpStatus.OK);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> deleteNumeroService(UUID uuid) {
        try {
            if(numeroServiceRepository.existsById(uuid)){
                numeroServiceRepository.deleteById(uuid);
                ns.put("message","Numéro service bien supprimé");
                return new ResponseEntity<Object>(ns,HttpStatus.OK);
            }else{
                ns.put("message", "Ce numéro de service n'existe pas");
                return new ResponseEntity<Object>(ns, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
