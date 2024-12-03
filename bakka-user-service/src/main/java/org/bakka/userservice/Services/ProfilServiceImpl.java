package org.bakka.userservice.Services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bakka.userservice.Dtos.ProfilRequestDto;
import org.bakka.userservice.Dtos.ProfilResponseDto;
import org.bakka.userservice.Entities.Profil;
import org.bakka.userservice.Mappers.ProfilMapper;
import org.bakka.userservice.Repositories.ProfilRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;
import java.util.*;


@Service
@Transactional
public class ProfilServiceImpl  implements  ProfilService{
    private final ProfilRepository profilRepository;
    private final ProfilMapper profilMapper;

    public ProfilServiceImpl(ProfilRepository profilRepository, ProfilMapper profilMapper) {
        this.profilRepository = profilRepository;
        this.profilMapper = profilMapper;
    }

    @Override
    public ResponseEntity<?> addProfil(ProfilRequestDto profilRequestDto) {

        Map<String, Object> res = new HashMap<>();
        try {
            if(profilRepository.existsProfilByLibelle(profilRequestDto.getLibelle())){
                res.put("message","Ce profil existe déjas");
               return new ResponseEntity<Object>(res, HttpStatus.BAD_REQUEST);
            }else{
                Profil profil=profilMapper.profilRequestDtoToProfil(profilRequestDto);
                Profil newProfil=profilRepository.save(profil);
                ProfilResponseDto profilResponseDto=profilMapper.profilToProfilResponseDto(newProfil);
                res.put("message","Enregistrement réussit");
                res.put("data",profilResponseDto);
                return new ResponseEntity<Object>(res,HttpStatus.CREATED);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }



    @Override
    public List<ProfilResponseDto>getAllProfils() {
        try {
           List<Profil> listProfils= profilRepository.findAll();
            return listProfils.stream().map(profilMapper::profilToProfilResponseDto).toList();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> getProfil(UUID id) {
        Map<String, Object> res = new HashMap<>();
        try {
            if(profilRepository.existsById(id)){
                Optional<Profil> profil=profilRepository.findById(id);
                res.put("data", profilMapper.profilToProfilResponseDto(profil.get()));
                return new ResponseEntity<Object>(res,HttpStatus.OK);
            }else{
                res.put("message","ce profil n'existe pas");
                return new ResponseEntity<Object>(res,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> updateProfil(UUID id, ProfilRequestDto profilRequestDto) {
        Map<String, Object> res = new HashMap<>();
        try {
            if(profilRepository.existsById(id)){
                Profil profil=profilMapper.profilRequestDtoToProfil(profilRequestDto);
                profil.setId(id);
                Profil updatedProfil=profilRepository.save(profil);
                ProfilResponseDto profilResponseDto=profilMapper.profilToProfilResponseDto(updatedProfil);
                res.put("data",profilResponseDto);
                res.put("message","profil édité");
                return new ResponseEntity<Object>(res,HttpStatus.OK);
            }else{
                res.put("message","ce profil n'existe pas");
                return new ResponseEntity<Object>(res,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> updateProfilFields(UUID id, Map<String, Object> fields) {
        Map<String, Object> res = new HashMap<>();
        try {
            Optional<Profil> existingProfil=profilRepository.findById(id);
            if(existingProfil.isPresent()){
                fields.forEach((key,value)->{
                    Field field=  ReflectionUtils.findField(Profil.class,key);
                    field.setAccessible(true);
                    ReflectionUtils.setField(field,existingProfil.get(),value);
                });
                Profil profil=profilRepository.save(existingProfil.get());
                res.put("data",profilMapper.profilToProfilResponseDto(profil));
                res.put("message","profil édité");
                return new ResponseEntity<Object>(res,HttpStatus.OK);
            }else{
                res.put("message","ce profil n'existe pas");
                return new ResponseEntity<Object>(res,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> deleteProfil(UUID id) {
        Map<String, Object> res = new HashMap<>();
        try {
            if(profilRepository.existsById(id)){
                profilRepository.deleteById(id);
                res.put("message","profil supprimé");
                return new ResponseEntity<Object>(res, HttpStatus.OK);
            }else{
                res.put("message","ce profil n'existe pas");
                return new ResponseEntity<Object>(res, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


}
