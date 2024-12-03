package org.bakka.userservice.Services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.bakka.userservice.Dtos.ClientBakkaResponseDto;
import org.bakka.userservice.Dtos.CompteServiceRequestDto;
import org.bakka.userservice.Dtos.CompteServiceResponseDto;
import org.bakka.userservice.Entities.ClientBakka;
import org.bakka.userservice.Entities.CompteService;
import org.bakka.userservice.Mappers.CompteServiceMapper;
import org.bakka.userservice.Repositories.ClientBakkaRepository;
import org.bakka.userservice.Repositories.CompteServiceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class CompteServiceServiceImpl implements CompteServiceService{

    private final CompteServiceMapper compteServiceMapper;
    private final CompteServiceRepository compteServiceRepository;
    private final ClientBakkaRepository clientBakkaRepository;

    Map<String, Object> cs = new HashMap<>();

    public CompteServiceServiceImpl(CompteServiceMapper compteServiceMapper, CompteServiceRepository compteServiceRepository, ClientBakkaRepository clientBakkaRepository) {
        this.compteServiceMapper = compteServiceMapper;
        this.compteServiceRepository = compteServiceRepository;
        this.clientBakkaRepository = clientBakkaRepository;
    }

    @Override
    public ResponseEntity<?> addCompteService(CompteServiceRequestDto compteServiceRequestDto) {
        try{
            CompteService compteService = compteServiceMapper.compteServiceRequestDtoToCompteService(compteServiceRequestDto);
            ClientBakka clientBakka = clientBakkaRepository.findById(compteServiceRequestDto.getClientBakka().getId()).orElseThrow(
                    ()-> new EntityNotFoundException("Client non trouvé")
            );
            compteService.setClientBakka(clientBakka);
            CompteService newCompteServie = compteServiceRepository.save(compteService);
            CompteServiceResponseDto compteServiceResponseDto = compteServiceMapper.compteServiceToCompteServiceResponseDto(newCompteServie);
            cs.put("message", "compte service ajouté avec succès");
            cs.put("data", compteServiceResponseDto);
            return new ResponseEntity<Object>(cs, HttpStatus.CREATED);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> getCompteService(UUID uuid) {
        try {
            if (compteServiceRepository.existsById(uuid)){
                CompteService compteService = compteServiceRepository.findById(uuid).get();
                CompteServiceResponseDto compteServiceResponseDto = compteServiceMapper.compteServiceToCompteServiceResponseDto(compteService);
                return  ResponseEntity.ok(compteServiceResponseDto);
            }else{
                cs.put("message", "Ce compte Service n'existe pas !");
                return new ResponseEntity<Object>(cs, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<CompteServiceResponseDto> getAllCompteService() {
        try {
            List<CompteService> compteServiceList = compteServiceRepository.findAll();
            return compteServiceList.stream().map(compteServiceMapper::compteServiceToCompteServiceResponseDto).toList();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> updateCompteService(CompteServiceRequestDto compteServiceRequestDto) {
        try {
            if(!compteServiceRepository.existsByNomFacturier(compteServiceRequestDto.getNomFacturier())){
                cs.put("message", "Ce nom de facturier n'existe pas");
                return new ResponseEntity<Object>(cs, HttpStatus.BAD_REQUEST);
            }else {
                CompteService compteService = compteServiceMapper.compteServiceRequestDtoToCompteService(compteServiceRequestDto);
                CompteService update = compteServiceRepository.save(compteService);
                CompteServiceResponseDto compteServiceResponseDto = compteServiceMapper.compteServiceToCompteServiceResponseDto(update);
                cs.put("message", "Ce nom facturier a été bien modifié");
                cs.put("data", compteServiceResponseDto);
                return new ResponseEntity<Object>(cs, HttpStatus.OK);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> deleteCompteService(UUID uuid) {
        try {
            if(compteServiceRepository.existsById(uuid)){
                compteServiceRepository.deleteById(uuid);
                cs.put("message", "compte Service supprimé");
                return new ResponseEntity<Object>(cs, HttpStatus.OK);
            }else {
                cs.put("message", "ce compte service n'existe pas");
                return new ResponseEntity<Object>(cs, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
