package org.bakka.userservice.Services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.bakka.userservice.Dtos.CompteBancaireResponseDto;

import org.bakka.userservice.Dtos.CompteBancaireRequestDto;
import org.bakka.userservice.Entities.ClientBakka;
import org.bakka.userservice.Entities.CompteBancaire;


import org.bakka.userservice.Enums.TypeCompte;
import org.bakka.userservice.Mappers.CompteBancaireMapper;
import org.bakka.userservice.Repositories.ClientBakkaRepository;
import org.bakka.userservice.Repositories.CompteBancaireRepository;
import org.bakka.userservice.Utils.UtilMethods;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

@Service
@Transactional
public class CompteBancaireServiceImpl implements CompteBancaireService{

    private final CompteBancaireRepository compteBancaireRepository;
    private final ClientBakkaRepository clientBakkaRepository;
    private final CompteBancaireMapper compteBancaireMapper;
    public CompteBancaireServiceImpl(CompteBancaireRepository compteBancaireRepository, ClientBakkaRepository clientBakkaRepository, CompteBancaireMapper compteBancaireMapper) {
        this.compteBancaireRepository = compteBancaireRepository;
        this.clientBakkaRepository = clientBakkaRepository;

        this.compteBancaireMapper = compteBancaireMapper;

    }

    @Override
    public ResponseEntity<?> addCompteBancaire(CompteBancaireRequestDto compteBancaireRequestDto) {
        Map<String, Object> res = new HashMap<>();
        try {
            if (compteBancaireRepository.existsByNumeroCompte(compteBancaireRequestDto.getNumeroCompte())){
                res.put("message","il existe déja un compte bancaire avec ce numéro de compte");
                return new ResponseEntity<Object>(res, HttpStatus.BAD_REQUEST);
            }else{
                CompteBancaire compteBancaire=compteBancaireMapper.compteBancaireRequestDtoToCompteBancaire(compteBancaireRequestDto);
                ClientBakka clientBakka= clientBakkaRepository.findById(compteBancaireRequestDto.getClientBakka().getId()).orElseThrow(
                        ()-> new EntityNotFoundException("Ce client bakka n'existe pas")
                );

                compteBancaire.setClientBakka(clientBakka);
                //we set the generated values
                compteBancaire.setSolde(UtilMethods.generateSolde());
                compteBancaire.setTypeCompte(TypeCompte.COMPTEBANK);
                CompteBancaire newCompteBancaire=compteBancaireRepository.save(compteBancaire);
                CompteBancaireResponseDto compteBancaireResponseDto=compteBancaireMapper.compteBancaireToCompteBancaireResponseDto(newCompteBancaire);
                res.put("message","compteBancaire ajouté");
                res.put("data",compteBancaireResponseDto);
                return new ResponseEntity<Object>(res,HttpStatus.CREATED);
            }


        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> getCompteBancaire(UUID id) {
        Map<String, Object> res = new HashMap<>();
        try {
            if(compteBancaireRepository.existsById(id)){
                Optional<CompteBancaire> compteBancaire=compteBancaireRepository.findById(id);
                CompteBancaireResponseDto compteBancaireResponseDto=compteBancaireMapper.compteBancaireToCompteBancaireResponseDto(compteBancaire.get());
                res.put("data", compteBancaireResponseDto);
                return new ResponseEntity<Object>(res,HttpStatus.OK);
            }else{
                res.put("message","cette compteBancaire n'existe pas");
                return new ResponseEntity<Object>(res,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public List<CompteBancaireResponseDto> getAllCompteBancaires() {
        try {
            List<CompteBancaire> listCompteBancaires= compteBancaireRepository.findAll();
            return listCompteBancaires.stream().map(compteBancaireMapper::compteBancaireToCompteBancaireResponseDto).toList();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> updateCompteBancaire(UUID id, CompteBancaireRequestDto compteBancaireRequestDto) {
        Map<String, Object> res = new HashMap<>();
        try{
            if (compteBancaireRepository.existsById(id)){
                CompteBancaire compteBancaire=compteBancaireMapper.compteBancaireRequestDtoToCompteBancaire(compteBancaireRequestDto);
                compteBancaire.setId(id);
                CompteBancaire updatedCompteBancaire=compteBancaireRepository.save(compteBancaire);
                CompteBancaireResponseDto compteBancaireResponseDto=compteBancaireMapper.compteBancaireToCompteBancaireResponseDto(updatedCompteBancaire);
                res.put("data",compteBancaireResponseDto);
                res.put("message","compteBancaire édité");
                return new ResponseEntity<Object>(res,HttpStatus.OK);
            }else{
                res.put("message","cette compteBancaire n'existe pas");
                return new ResponseEntity<Object>(res,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> updateCompteBancaireFields(UUID id, Map<String, Object> fields) {
        Map<String, Object> res = new HashMap<>();
        try {
            Optional<CompteBancaire> existingCompteBancaire=compteBancaireRepository.findById(id);
            if(existingCompteBancaire.isPresent()){
                fields.forEach((key,value)->{
                    Field field= ReflectionUtils.findField(CompteBancaire.class,key);
                    field.setAccessible(true);
                    ReflectionUtils.setField(field,existingCompteBancaire.get(),value);
                });
                CompteBancaire compteBancaire=compteBancaireRepository.save(existingCompteBancaire.get());
                res.put("data",compteBancaireMapper.compteBancaireToCompteBancaireResponseDto(compteBancaire));
                res.put("message","compteBancaire édité");
                return new ResponseEntity<Object>(res,HttpStatus.OK);
            }else{
                res.put("message","ce compteBancaire n'existe pas");
                return new ResponseEntity<Object>(res,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> deleteCompteBancaire(UUID id) {
        Map<String, Object> res = new HashMap<>();
        try {
            if(compteBancaireRepository.existsById(id)){
                compteBancaireRepository.deleteById(id);
                res.put("message","compteBancaire supprimé");
                return new ResponseEntity<Object>(res, HttpStatus.OK);
            }else{
                res.put("message","ce compteBancaire n'existe pas");
                return new ResponseEntity<Object>(res, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> getAllClientBankAccounts(UUID id) {
        Map<String, Object> res = new HashMap<>();
        try {
            if(clientBakkaRepository.existsById(id)){
                List<CompteBancaire> compteBancaires=compteBancaireRepository.getAllByClientBakkaId(id);
                return ResponseEntity.ok(compteBancaires);
            }else{
                res.put("message","ce client n'existe pas");
                return new ResponseEntity<Object>(res, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
