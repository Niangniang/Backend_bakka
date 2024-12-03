package org.bakka.userservice.Services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bakka.userservice.Dtos.BanqueRequestDto;
import org.bakka.userservice.Dtos.BanqueResponseDto;
import org.bakka.userservice.Entities.Banque;
import org.bakka.userservice.Mappers.BanqueMapper;
import org.bakka.userservice.Mappers.ProfilMapper;
import org.bakka.userservice.Repositories.BanqueRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

@Service
@Transactional
public class BanqueServiceImpl implements BanqueService{
    private final BanqueRepository banqueRepository;
    private final BanqueMapper banqueMapper;
    private final AzureStorageService azureStorageService;
    public BanqueServiceImpl(BanqueRepository banqueRepository, BanqueMapper banqueMapper,AzureStorageService azureStorageService) {
        this.banqueRepository = banqueRepository;
        this.banqueMapper = banqueMapper;
        this.azureStorageService = azureStorageService;
    }



    @Override
    public ResponseEntity<?> addBanque(BanqueRequestDto banqueRequestDto) {
        Map<String, Object> res = new HashMap<>();
        try {
            if(banqueRepository.existsBanqueByIntitule(banqueRequestDto.getIntitule())){
                res.put("message","Il existe déjas une banque avec cet intitulé");
                return new ResponseEntity<Object>(res, HttpStatus.BAD_REQUEST);
            }else{
                String imageUrl= azureStorageService.uploadFile(
                        banqueRequestDto.getImageUrl().getInputStream(),
                        banqueRequestDto.getImageUrl().getOriginalFilename(),
                        banqueRequestDto.getImageUrl().getSize()
                );

                Banque banque=banqueMapper.banqueRequestDtoToBanque(banqueRequestDto);
                banque.setImageUrl(imageUrl);
                Banque newBanque=banqueRepository.save(banque);
                BanqueResponseDto banqueResponseDto=banqueMapper.banqueToBanqueResponseDto(newBanque);
                res.put("message","banque ajouté");
                res.put("data",banqueResponseDto);
                return new ResponseEntity<Object>(res,HttpStatus.CREATED);
            }
        }catch (Exception e){

            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> getBanque(UUID id) {
        Map<String, Object> res = new HashMap<>();
        try {
            if(banqueRepository.existsById(id)){
                Optional<Banque> banque=banqueRepository.findById(id);
                BanqueResponseDto banqueResponseDto=banqueMapper.banqueToBanqueResponseDto(banque.get());
                res.put("data", banqueResponseDto);
                return new ResponseEntity<Object>(res,HttpStatus.OK);
            }else{
                res.put("message","cette banque n'existe pas");
                return new ResponseEntity<Object>(res,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public List<BanqueResponseDto> getAllBanques() {
        try {
            List<Banque> listBanques= banqueRepository.findAll();
            return listBanques.stream().map(banqueMapper::banqueToBanqueResponseDto).toList();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> updateBanque(UUID id, BanqueRequestDto banqueRequestDto) {
        Map<String, Object> res = new HashMap<>();
        try{
            if (banqueRepository.existsById(id)){
                Banque banque=banqueMapper.banqueRequestDtoToBanque(banqueRequestDto);
                banque.setId(id);
                Banque updatedBanque=banqueRepository.save(banque);
                BanqueResponseDto banqueResponseDto=banqueMapper.banqueToBanqueResponseDto(updatedBanque);
                res.put("data",banqueResponseDto);
                res.put("message","banque édité");
                return new ResponseEntity<Object>(res,HttpStatus.OK);
            }else{
                res.put("message","cette banque n'existe pas");
                return new ResponseEntity<Object>(res,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> updateBanqueFields(UUID id, Map<String, Object> fields) {

        Map<String, Object> res = new HashMap<>();
        try {
            Optional<Banque> existingBanque=banqueRepository.findById(id);
            if(existingBanque.isPresent()){
                fields.forEach((key,value)->{
                    Field field=ReflectionUtils.findField(Banque.class,key);
                    field.setAccessible(true);
                    ReflectionUtils.setField(field,existingBanque.get(),value);
                });
                Banque banque=banqueRepository.save(existingBanque.get());
                res.put("data",banqueMapper.banqueToBanqueResponseDto(banque));
                res.put("message","banque édité");
                return new ResponseEntity<Object>(res,HttpStatus.OK);
            }else{
                res.put("message","cette banque n'existe pas");
                return new ResponseEntity<Object>(res,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> deleteBanque(UUID id) {
        Map<String, Object> res = new HashMap<>();
        try {
            if(banqueRepository.existsById(id)){
                banqueRepository.deleteById(id);
                res.put("message","banque supprimé");
                return new ResponseEntity<Object>(res, HttpStatus.OK);
            }else{
                res.put("message","cette banque n'existe pas");
                return new ResponseEntity<Object>(res, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
