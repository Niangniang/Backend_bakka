package org.bakka.userservice.Services;

import jakarta.transaction.Transactional;
import org.bakka.userservice.Dtos.OperationResponseDto;

import org.bakka.userservice.Dtos.OperationRequestDto;


import org.bakka.userservice.Entities.Operation;

import org.bakka.userservice.Mappers.OperationMapper;
import org.bakka.userservice.Repositories.OperationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

@Service
@Transactional
public class OperationServiceImpl implements OperationService{
    private final OperationRepository operationRepository;
    private final OperationMapper operationMapper;

    public OperationServiceImpl(OperationRepository operationRepository, OperationMapper operationMapper) {
        this.operationRepository = operationRepository;
        this.operationMapper = operationMapper;
    }

    @Override
    public ResponseEntity<?> addOperation(OperationRequestDto operationRequestDto) {
        Map<String, Object> res = new HashMap<>();
        try {
            Operation operation=operationMapper.operationRequestDtoToOperation(operationRequestDto);
            Operation newOperation=operationRepository.save(operation);
            OperationResponseDto operationResponseDto=operationMapper.operationToOperationResponseDto(newOperation);
            res.put("message","operation ajouté");
            res.put("data",operationResponseDto);
            return new ResponseEntity<Object>(res, HttpStatus.CREATED);

        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public ResponseEntity<?> getOperation(UUID id) {
        Map<String, Object> res = new HashMap<>();
        try {
            if(operationRepository.existsById(id)){
                Optional<Operation> operation=operationRepository.findById(id);
                OperationResponseDto operationResponseDto=operationMapper.operationToOperationResponseDto(operation.get());
                res.put("data", operationResponseDto);
                return new ResponseEntity<Object>(res,HttpStatus.OK);
            }else{
                res.put("message","cette opération n'existe pas");
                return new ResponseEntity<Object>(res,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public List<OperationResponseDto> getAllOperations() {
        try {
            List<Operation> listOperations= operationRepository.findAll();
            return listOperations.stream().map(operationMapper::operationToOperationResponseDto).toList();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> updateOperation(UUID id, OperationRequestDto operationRequestDto) {
        Map<String, Object> res = new HashMap<>();
        try{
            if (operationRepository.existsById(id)){
                Operation operation=operationMapper.operationRequestDtoToOperation(operationRequestDto);
                operation.setId(id);
                Operation updatedOperation=operationRepository.save(operation);
                OperationResponseDto operationResponseDto=operationMapper.operationToOperationResponseDto(updatedOperation);
                res.put("data",operationResponseDto);
                res.put("message","opération édité");
                return new ResponseEntity<Object>(res,HttpStatus.OK);
            }else{
                res.put("message","cette opération n'existe pas");
                return new ResponseEntity<Object>(res,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> updateOperationFields(UUID id, Map<String, Object> fields) {
        Map<String, Object> res = new HashMap<>();
        try {
            Optional<Operation> existingOperation=operationRepository.findById(id);
            if(existingOperation.isPresent()){
                fields.forEach((key,value)->{
                    Field field= ReflectionUtils.findField(Operation.class,key);
                    field.setAccessible(true);
                    ReflectionUtils.setField(field,existingOperation.get(),value);
                });
                Operation operation=operationRepository.save(existingOperation.get());
                res.put("data",operationMapper.operationToOperationResponseDto(operation));
                res.put("message","operation édité");
                return new ResponseEntity<Object>(res,HttpStatus.OK);
            }else{
                res.put("message","ce operation n'existe pas");
                return new ResponseEntity<Object>(res,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> deleteOperation(UUID id) {
        Map<String, Object> cw = new HashMap<>();
        try {
            if(operationRepository.existsById(id)){
                operationRepository.deleteById(id);
                cw.put("message","compte wallet supprimé");
                return new ResponseEntity<Object>(cw, HttpStatus.OK);
            }else{
                cw.put("message","ce com^pte wallet n'existe pas");
                return new ResponseEntity<Object>(cw, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<OperationResponseDto> historiqueCompte(UUID id) {
        Map<String, Object> res = new HashMap<>();
        try {
             List<Operation> listOperations= operationRepository.findOperationsByCompteId(id);
             return listOperations.stream().map(operationMapper::operationToOperationResponseDto).toList();
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }
}
