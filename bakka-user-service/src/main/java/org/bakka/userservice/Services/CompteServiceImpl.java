package org.bakka.userservice.Services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.bakka.userservice.Dtos.*;

import org.bakka.userservice.Entities.Compte;

import org.bakka.userservice.Entities.Operation;
import org.bakka.userservice.Mappers.CompteMapper;
import org.bakka.userservice.Mappers.OperationMapper;
import org.bakka.userservice.Repositories.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class CompteServiceImpl implements CompteService{
    private final CompteMapper compteMapper;
    private final OperationMapper operationMapper;
    private final CompteRepository compteRepository;
    private final OperationRepository operationRepository;
    private final CompteBancaireRepository compteBancaireRepository;
    private final CompteWalletRepository compteWalletRepository;
    private final ClientBakkaRepository clientBakkaRepository;

    public CompteServiceImpl(CompteMapper compteMapper, CompteRepository compteRepository, OperationMapper operationMapper, OperationRepository operationRepository, CompteBancaireRepository compteBancaireRepository, CompteWalletRepository compteWalletRepository, ClientBakkaRepository clientBakkaRepository) {
        this.compteMapper = compteMapper;
        this.compteRepository = compteRepository;
        this.operationMapper = operationMapper;
        this.operationRepository = operationRepository;
        this.compteBancaireRepository = compteBancaireRepository;
        this.compteWalletRepository = compteWalletRepository;
        this.clientBakkaRepository = clientBakkaRepository;
    }

    @Override
    public ResponseEntity<?> transferAccountToAccount(TransferRequestDto transferRequestDto) {
        Map<String, Object> res = new HashMap<>();
            try {
            Compte source=compteRepository.findById(transferRequestDto.getSourceId()).orElseThrow(
                    ()-> new EntityNotFoundException("Le compte source n'existe pas")
            );
            Compte destination=compteRepository.findById(transferRequestDto.getDestinationId()).orElseThrow(
                    ()-> new EntityNotFoundException("Le compte destination n'existe pas")
            );
            if(transferRequestDto.getMontant()>source.getSolde()){
                res.put("message","impossible d'éffectuer cette opération le montant à transférer est supérieur au solde du compte à débiter");
                return new ResponseEntity<Object>(res,HttpStatus.BAD_REQUEST);
            }else{
                source.setSolde(source.getSolde()- transferRequestDto.getMontant());
                source.setDateSolde(LocalDateTime.now());
                destination.setSolde(destination.getSolde()+ transferRequestDto.getMontant());
                destination.setDateSolde(LocalDateTime.now());
                compteRepository.save(source);
                compteRepository.save(destination);
                OperationRequestDto operationRequestDto=new OperationRequestDto(source,destination, transferRequestDto.getMontant(), 0,true);
                Operation newOperation=operationMapper.operationRequestDtoToOperation(operationRequestDto);
                operationRepository.save(newOperation);
                OperationResponseDto operationResponseDto=operationMapper.operationToOperationResponseDto(newOperation);
                res.put("message","opération effectué avec succés");
                res.put("data",operationResponseDto);
                return new ResponseEntity<Object>(res,HttpStatus.OK);
            }
            }catch (Exception e){
                throw new RuntimeException(e);
            }
    }

    @Override
    public ResponseEntity<?> addCompte(CompteRequestDto compteRequestDto) {
        return null;
    }

    @Override
    public ResponseEntity<?> getCompte(UUID id) {
        Map<String, Object> res = new HashMap<>();
        try {
            if(compteRepository.existsById(id)){
                Optional<Compte> compte=compteRepository.findById(id);
                CompteResponseDto compteResponseDto=compteMapper.compteToCompteResponseDto(compte.get());
                res.put("data", compteResponseDto);
                return new ResponseEntity<Object>(res,HttpStatus.OK);
            }else{
                res.put("message","ce compte n'existe pas");
                return new ResponseEntity<Object>(res,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public List<CompteResponseDto> getAllComptes() {
        try {
            List<Compte> listComptes= compteRepository.findAll();
            return listComptes.stream().map(compteMapper::compteToCompteResponseDto).toList();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> updateCompte(UUID id, CompteRequestDto compteRequestDto) {
        Map<String, Object> res = new HashMap<>();
        try{
            if (compteRepository.existsById(id)){
                Compte compte=compteMapper.compteRequestDtoToCompte(compteRequestDto);
                compte.setId(id);
                Compte updatedCompte=compteRepository.save(compte);
                CompteResponseDto compteResponseDto=compteMapper.compteToCompteResponseDto(updatedCompte);
                res.put("data",compteResponseDto);
                res.put("message","compte édité");
                return new ResponseEntity<Object>(res,HttpStatus.OK);
            }else{
                res.put("message","ce n'existe pas");
                return new ResponseEntity<Object>(res,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> updateCompteFields(UUID id, Map<String, Object> fields) {
        Map<String, Object> res = new HashMap<>();
        try {
            Optional<Compte> existingCompte=compteRepository.findById(id);
            if(existingCompte.isPresent()){
                fields.forEach((key,value)->{
                    Field field= ReflectionUtils.findField(Compte.class,key);
                    field.setAccessible(true);
                    ReflectionUtils.setField(field,existingCompte.get(),value);
                });
                Compte compte=compteRepository.save(existingCompte.get());
                res.put("data",compteMapper.compteToCompteResponseDto(compte));
                res.put("message","compte édité");
                return new ResponseEntity<Object>(res,HttpStatus.OK);
            }else{
                res.put("message","ce compte n'existe pas");
                return new ResponseEntity<Object>(res,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> deleteCompte(UUID id) {
        Map<String, Object> res = new HashMap<>();
        try {
            if(compteRepository.existsById(id)){
                compteRepository.deleteById(id);
                res.put("message","compte supprimé");
                return new ResponseEntity<Object>(res, HttpStatus.OK);
            }else{
                res.put("message","ce compte n'existe pas");
                return new ResponseEntity<Object>(res, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> getAllAccountsExceptBakka(UUID id) {
        Map<String, Object> res = new HashMap<>();
        try {
            if(clientBakkaRepository.existsById(id)){

            List<Compte> listComptes= new ArrayList<>();
            listComptes.addAll(compteWalletRepository.getAllByClientBakkaId(id));
            listComptes.addAll(compteBancaireRepository.getAllByClientBakkaId(id));
            return ResponseEntity.ok(listComptes);
            }else{
              res.put("message","Ce client n'existe pas");
              return new ResponseEntity<Object>(res,HttpStatus.BAD_REQUEST);
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
