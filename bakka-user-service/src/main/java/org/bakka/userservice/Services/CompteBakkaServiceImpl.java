package org.bakka.userservice.Services;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.bakka.userservice.Dtos.CompteBakkaRequestDto;
import org.bakka.userservice.Dtos.CompteBakkaResponseDto;
import org.bakka.userservice.Entities.*;
import org.bakka.userservice.Mappers.CompteBakkaMapper;
import org.bakka.userservice.RecodDtos.AddBenificiaireDto;
import org.bakka.userservice.RecodDtos.ChangerPlafondRequestDto;
import org.bakka.userservice.Repositories.BeneficiaireRepository;
import org.bakka.userservice.Repositories.ClientBakkaRepository;
import org.bakka.userservice.Repositories.CompteBakkaRepository;
import org.bakka.userservice.Repositories.ParameterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

@Service
@Transactional
public class CompteBakkaServiceImpl implements CompteBakkaService {

    private final CompteBakkaMapper compteBakkaMapper;
    private  final CompteBakkaRepository compteBakkaRepository;
    private final ParameterRepository parameterRepository;
    Map<String, Object> cb = new HashMap<>();
    private final ClientBakkaRepository clientBakkaRepository;
    private final BeneficiaireRepository beneficiaireRepository;

    public CompteBakkaServiceImpl(CompteBakkaMapper compteBakkaMapper, CompteBakkaRepository compteBakkaRepository,
                                  ParameterRepository parameterRepository, ClientBakkaRepository clientBakkaRepository,
                                  BeneficiaireRepository beneficiaireRepository) {
        this.compteBakkaMapper = compteBakkaMapper;
        this.compteBakkaRepository = compteBakkaRepository;
        this.parameterRepository = parameterRepository;
        this.clientBakkaRepository = clientBakkaRepository;
        this.beneficiaireRepository = beneficiaireRepository;
    }

    @Override
    public ResponseEntity<?> addcompteBakka(CompteBakkaRequestDto compteBakkaRequestDto) {
        try {
            CompteBakka compteBakka = compteBakkaMapper.compteBakkaRequestDtoToCompteBakka(compteBakkaRequestDto);
            ClientBakka clientBakka = clientBakkaRepository.findById(compteBakkaRequestDto.getClientBakka().getId()).orElseThrow(
                    ()-> new EntityNotFoundException("Client non trouvé")
            );
            compteBakka.setClientBakka(clientBakka);
            CompteBakka newCompteBakka=compteBakkaRepository.save(compteBakka);
            CompteBakkaResponseDto compteBakkaResponseDto =compteBakkaMapper.compteBakkaToCompteBakkaResponseDto(newCompteBakka);
             cb.put("message","compte Bakka ajouté aveec succès");
             cb.put("data",compteBakkaResponseDto);
            return new ResponseEntity<Object>(cb, HttpStatus.CREATED);

        }catch (Exception e){

            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> getcompteBakka(UUID id) {
        Map<String, Object> cb = new HashMap<>();
        try {
            if(compteBakkaRepository.existsById(id)){
                CompteBakka compteBakka=compteBakkaRepository.findById(id).get();
                CompteBakkaResponseDto compteBakkaResponseDto= compteBakkaMapper.compteBakkaToCompteBakkaResponseDto(compteBakka);
                return ResponseEntity.ok(compteBakkaResponseDto);
            }else{
                cb.put("message","Ce compte Bakka n'existe pas");
                return new ResponseEntity<Object>(cb,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public List<CompteBakkaResponseDto> getAllCompteBakka() {
        try {
            List<CompteBakka> compteBakkaList= compteBakkaRepository.findAll();
            return compteBakkaList.stream().map(compteBakkaMapper::compteBakkaToCompteBakkaResponseDto).toList();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> updateCompteBakka(UUID id, CompteBakkaRequestDto compteBakkaRequestDto) {
        try {
            if(compteBakkaRepository.existsById(id)){
                CompteBakka compteBakka = compteBakkaMapper.compteBakkaRequestDtoToCompteBakka(compteBakkaRequestDto);
                compteBakka.setId(id);
                CompteBakka updatedCompteBakka=compteBakkaRepository.save(compteBakka);
                CompteBakkaResponseDto compteBakkaResponseDto= compteBakkaMapper.compteBakkaToCompteBakkaResponseDto(updatedCompteBakka);
                cb.put("data", compteBakkaResponseDto);
                cb.put("message","compte Bakka édité");
                return new ResponseEntity<Object>(cb,HttpStatus.OK);
            }else{
                cb.put("message","ce compte Bakka n'existe pas");
                return new ResponseEntity<Object>(cb,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> updateCompteBakkaFields(UUID id, Map<String, Object> fields) {
        Map<String, Object> cb = new HashMap<>();
        try {
            CompteBakka existedcompteBakka=compteBakkaRepository.findById(id).orElseThrow(
                    ()-> new EntityNotFoundException("Compte  Bakka non trouvé")
            );

            fields.forEach((key,value)->{
                Field field=  ReflectionUtils.findField(CompteBakka.class,key);
                field.setAccessible(true);
                ReflectionUtils.setField(field,existedcompteBakka,value);
            });
            CompteBakka compteBakka=compteBakkaRepository.save(existedcompteBakka);
            cb.put("data", compteBakkaMapper.compteBakkaToCompteBakkaResponseDto(compteBakka));
            cb.put("message","compte Bakka partiellement edité");
            return new ResponseEntity<Object>(cb,HttpStatus.OK);

        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> deleteCompteBakka(UUID id) {
        Map<String, Object> cb = new HashMap<>();
        try {
            if(compteBakkaRepository.existsById(id)){
                compteBakkaRepository.deleteById(id);
                cb.put("message","compte Bakka supprimé");
                return new ResponseEntity<Object>(cb, HttpStatus.OK);
            }else{
                cb.put("message","ce compte Bakka n'existe pas");
                return new ResponseEntity<Object>(cb, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> chargerCompte(UUID id, String somme) {
        Map<String, Object> cb = new HashMap<>();
        CompteBakka compteBakka = compteBakkaRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(" Compte Bakka non trouvé"));
        compteBakka.setSolde(20);
        compteBakkaRepository.save(compteBakka);

        CompteBakkaResponseDto compteBakkaResponseDto = compteBakkaMapper.compteBakkaToCompteBakkaResponseDto(compteBakka);
        cb.put("message","compte Bakka mis à jour");
        cb.put("data", compteBakkaResponseDto );

        return new ResponseEntity<Object>(cb, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> changerPlafond(ChangerPlafondRequestDto changerPlafondRequestDto) {
        Map<String, Object> res = new HashMap<>();
        try {
            Optional<CompteBakka> compteBakkaOpt = compteBakkaRepository.findById(changerPlafondRequestDto.compteBakkaId());
            Optional<Parameter> newParameterOpt = parameterRepository.findById(changerPlafondRequestDto.plafondId());

            if (compteBakkaOpt.isEmpty()) {
                res.put("message", "Ce compte Bancaire n'existe pas");
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            } else if (newParameterOpt.isEmpty()) {
                res.put("message", "Ce paramètre plafond n'existe pas");
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            } else {
                CompteBakka compteBakka = compteBakkaOpt.get();
                Parameter newParameter = newParameterOpt.get();

                Set<Parameter> parameters = compteBakka.getParameters();
                // Trouver le paramètre existant commençant par "PLAFOND_"
                Optional<Parameter> existingPlafondParam = parameters.stream()
                        .filter(p -> p.getLibelle().startsWith("PLAFOND_"))
                        .findFirst();

                if (existingPlafondParam.isPresent()) {
                    // Remplacer par le nouveau paramètre
                    parameters.remove(existingPlafondParam.get());
                    parameters.add(newParameter);
                } else {
                    // Ajouter le nouveau paramètre si aucun existant
                    parameters.add(newParameter);
                }

                compteBakka.setParameters(parameters);
                CompteBakka updatedCompteBakka=compteBakkaRepository.save(compteBakka);
                res.put("data",updatedCompteBakka);
                res.put("message", "Le plafond a été mis à jour avec succès");
                return new ResponseEntity<>(res, HttpStatus.OK);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> addBenificiaire(UUID id, AddBenificiaireDto addBenificiaireDto) {
        Map<String, Object> cb = new HashMap<>();
        try {
            CompteBakka compteBakka = compteBakkaRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Comte Bakka non trouvé"));
            ClientBakka clientBakka = clientBakkaRepository.findByUsername(addBenificiaireDto.userName()).orElseThrow(()-> new EntityNotFoundException("Client Bakka non trouvé"));
            CompteBakka cmp_benif = compteBakkaRepository.findByClientBakka(clientBakka).orElseThrow(()-> new EntityNotFoundException("Aucun compte benificaire correspondant"));

            Beneficiaire beneficiaire = beneficiaireRepository.findByNumeroCompte(cmp_benif.getNumeroCompte()).orElseThrow(()-> new EntityNotFoundException("Aucun Beneficiare avec ce Compte "));

            Set<Beneficiaire> beneficiaires = compteBakka.getBeneficiaires();
            if (beneficiaires == null) {
                beneficiaires = new HashSet<>();
                compteBakka.setBeneficiaires(beneficiaires);
            }else {
                for (Beneficiaire cmpb : compteBakka.getBeneficiaires()){
                    if (cmpb == beneficiaire){
                        cb.put("message", "Bénéficiaire dééjà ajouté");
                        return new ResponseEntity<Object>(cb, HttpStatus.BAD_REQUEST);
                    }
                }
            }

            beneficiaires.add(beneficiaire);
            compteBakkaRepository.save(compteBakka);

            CompteBakkaResponseDto compteBakkaResponseDto = compteBakkaMapper.compteBakkaToCompteBakkaResponseDto(compteBakka);
            cb.put("message", "Bénéficiaire ajouté avec succès");
            cb.put("data", compteBakkaResponseDto);
            return new ResponseEntity<Object>(cb, HttpStatus.OK);

        }catch (EntityNotFoundException e){
            cb.put("message", e.getMessage());
            return new ResponseEntity<Object>(cb, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> retirerBenificiaire(UUID id, AddBenificiaireDto addBenificiaireDto) {
        Map<String, Object> cb = new HashMap<>();
        try {
            CompteBakka compteBakka = compteBakkaRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Comte Bakka non trouvé"));
            ClientBakka clientBakka = clientBakkaRepository.findByUsername(addBenificiaireDto.userName()).orElseThrow(()-> new EntityNotFoundException("Client Bakka non trouvé"));
            CompteBakka compte = compteBakkaRepository.findByClientBakka(clientBakka).orElseThrow(()-> new EntityNotFoundException("Aucun compte benificaire correspondant"));

            Beneficiaire beneficiaire = beneficiaireRepository.findByNumeroCompte(compte.getNumeroCompte()).orElseThrow(()-> new EntityNotFoundException("Aucun Beneficiaire avec ce compyte"));

            for (Beneficiaire cmp : compteBakka.getBeneficiaires()){
                if (cmp == beneficiaire){
                    compteBakka.getBeneficiaires().remove(beneficiaire);
                    compteBakkaRepository.save(compteBakka);
                }
            }

            CompteBakkaResponseDto compteBakkaResponseDto = compteBakkaMapper.compteBakkaToCompteBakkaResponseDto(compteBakka);
            cb.put("message", "Bénéficiaire retiré avec succès");
            cb.put("data", compteBakkaResponseDto);
            return new ResponseEntity<Object>(cb, HttpStatus.OK);

        }catch (EntityNotFoundException e){
            cb.put("message", e.getMessage());
            return new ResponseEntity<Object>(cb, HttpStatus.NOT_FOUND);
        }
    }

}
