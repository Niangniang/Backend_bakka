package org.bakka.userservice.Services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.bakka.userservice.Dtos.BanqueResponseDto;
import org.bakka.userservice.Dtos.CarteRequestDto;
import org.bakka.userservice.Dtos.CarteResponseDto;
import org.bakka.userservice.Dtos.ProfilResponseDto;
import org.bakka.userservice.Entities.Banque;
import org.bakka.userservice.Entities.Carte;
import org.bakka.userservice.Entities.Profil;
import org.bakka.userservice.Mappers.BanqueMapper;
import org.bakka.userservice.Mappers.CarteMapper;
import org.bakka.userservice.Repositories.CarteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class CarteServiceImpl implements CarteService {

    private final CarteRepository carteRepository;
    private final CarteMapper carteMapper;

    public CarteServiceImpl(CarteRepository carteRepository, CarteMapper carteMapper) {
        this.carteRepository = carteRepository;
        this.carteMapper = carteMapper;
    }


    /*
    @Override
    public ResponseEntity<?> addCarte(CarteRequestDto carteRequestDto){

        Map<String, Object> res = new HashMap<>();
        try {
            if(carteRepository.existsCarteByCompteBakka(carteRequestDto.getCompteBakka())){
                res.put("message","Carte déja existante pour ce compte");
                return new ResponseEntity<Object>(res, HttpStatus.BAD_REQUEST);
            }else{
                Carte carte=carteMapper.carteRequestDtoToCarte(carteRequestDto);
                Carte newCarte=carteRepository.save(carte);
                CarteResponseDto carteResponseDto=carteMapper.carteToCarteResponseDto(newCarte);
                res.put("message","carte ajoutée avec succés");
                res.put("data",carteResponseDto);
                return new ResponseEntity<Object>(res,HttpStatus.CREATED);
            }
        }catch (Exception e){

            throw new RuntimeException(e);
        }

    }
    */

    @Override
    public ResponseEntity<?> getCarte(UUID id){

        Map<String, Object> result = new HashMap<>();

        try {
            if(!carteRepository.existsById(id)){
                result.put("message","Carte id inexistante : " + id);
                return new ResponseEntity<Object>(result,HttpStatus.BAD_REQUEST);
            }else{
                Carte carte=carteRepository.findById(id).get();
                result.put("requestID", id);
                result.put("carte", carteMapper.carteToCarteResponseDto(carte));
                return new ResponseEntity<Object>(result,HttpStatus.OK);
            }
        }catch (Exception e){
            throw  new  RuntimeException(e);
        }

    }
    @Override
    public ResponseEntity<?> updateCarte(UUID id, CarteRequestDto carteRequestDto){

        Map<String, Object> result = new HashMap<>();
        try {
            if(carteRepository.existsById(id)){
                Carte carte=carteMapper.carteRequestDtoToCarte(carteRequestDto);
                carte.setId(id);
                Carte updatedCarte=carteRepository.save(carte);
                CarteResponseDto carteResponseDto=carteMapper.carteToCarteResponseDto(updatedCarte);
                result.put("updatedCarte",carteResponseDto);
                result.put("message","carte mise à jour avec succes");
                return new ResponseEntity<Object>(result,HttpStatus.OK);
            }else{
                result.put("message","carte inexistante");
                return new ResponseEntity<Object>(result,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw  new RuntimeException(e);
        }

    }
    @Override
    public ResponseEntity<?> deleteCarte(UUID id){
        Map<String, Object> result = new HashMap<>();
        try {
            if(carteRepository.existsById(id)){
                carteRepository.deleteById(id);
                result.put("message","carte supprimée avec succés");
                return new ResponseEntity<Object>(result, HttpStatus.OK);
            }else{
                result.put("message","carte inexistante");
                return new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /*
    @Override
    public ResponseEntity<?> soldeCarte(UUID id, CarteRequestDto carteRequestDto){

        Map<String, Object> result = new HashMap<>();
        try {
            if(carteRepository.existsById(id)){
                Carte carte=carteMapper.carteRequestDtoToCarte(carteRequestDto);
                CarteResponseDto carteResponseDto= carteMapper.carteToCarteResponseDto(carte);
                double soldecarte = carte.getCompteBakka().getSolde();
                result.put("carte",carteResponseDto);
                result.put("soldeCarte",soldecarte);
                return new ResponseEntity<Object>(result,HttpStatus.OK);
            }else{
                result.put("message","carte inexistante");
                return new ResponseEntity<Object>(result,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }
    */

    @Override
    public ResponseEntity<?> demandeCartePhysique(UUID id, CarteRequestDto carteRequestDto) {

        Map<String, Object> result = new HashMap<>();
        try {
            if (carteRepository.existsById(id)) {
                Carte carte = carteMapper.carteRequestDtoToCarte(carteRequestDto);
                carte.setId(id);
                carte.setPhysique(true);
                Carte updatedCarte = carteRepository.save(carte);
                CarteResponseDto carteResponseDto = carteMapper.carteToCarteResponseDto(updatedCarte);
                result.put("updatedCarte", carteResponseDto);
                result.put("message", "demande de carte physique enrégistrée avec succés");
                return new ResponseEntity<Object>(result, HttpStatus.OK);
            } else {
                result.put("message", "carte inexistante");
                return new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
