package org.bakka.userservice.Services;

import jakarta.transaction.Transactional;
import org.bakka.userservice.Dtos.ParameterResponseDto;
import org.bakka.userservice.Dtos.ParameterRequestDto;
import org.bakka.userservice.Entities.Parameter;
import org.bakka.userservice.Mappers.ParameterMapper;
import org.bakka.userservice.Repositories.ParameterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

@Service
@Transactional
public class ParameterServiceImpl implements ParameterService{
    private final ParameterRepository parameterRepository;
    private final ParameterMapper parameterMapper;

    public ParameterServiceImpl(ParameterRepository parameterRepository, ParameterMapper parameterMapper) {
        this.parameterRepository = parameterRepository;
        this.parameterMapper = parameterMapper;
    }

    @Override
    public ResponseEntity<?> addParameter(ParameterRequestDto parameterRequestDto) {
        Map<String, Object> res = new HashMap<>();
        try {
            if(parameterRepository.existsByLibelle(parameterRequestDto.getLibelle())){
                res.put("message","il existe déja un parametre avec ce libellé");
                return new ResponseEntity<Object>(res, HttpStatus.BAD_REQUEST);
            }else{
                Parameter parameter=parameterMapper.parameterRequestDtoToParameter(parameterRequestDto);
                Parameter newParameter=parameterRepository.save(parameter);
                ParameterResponseDto parameterResponseDto=parameterMapper.parameterToParameterResponseDto(newParameter);
                res.put("data",parameterResponseDto);
                res.put("message","parameter crée");
                return new ResponseEntity<Object>(res,HttpStatus.CREATED);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ParameterResponseDto> getAllParameters() {
        try {
            List<Parameter> listParameters= parameterRepository.findAll();
            return listParameters.stream().map(parameterMapper::parameterToParameterResponseDto).toList();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> getParameter(UUID id) {
        Map<String, Object> res = new HashMap<>();
        try {
          if(parameterRepository.existsById(id)){
              Optional<Parameter> parameter=parameterRepository.findById(id);
              ParameterResponseDto parameterResponseDto=parameterMapper.parameterToParameterResponseDto(parameter.get());
              res.put("data",parameterResponseDto);
              return new ResponseEntity<Object>(res,HttpStatus.OK);
          }else{
              res.put("message","ce parametre n'existe pas");
              return new ResponseEntity<Object>(res,HttpStatus.BAD_REQUEST);
          }
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> updateParameter(UUID id, ParameterRequestDto parameterRequestDto) {
        Map<String, Object> res = new HashMap<>();
        try{
            if (parameterRepository.existsById(id)){
                Parameter parameter=parameterMapper.parameterRequestDtoToParameter(parameterRequestDto);
                parameter.setId(id);
                Parameter updatedParameter=parameterRepository.save(parameter);
                ParameterResponseDto parameterResponseDto=parameterMapper.parameterToParameterResponseDto(updatedParameter);
                res.put("data",parameterResponseDto);
                res.put("message","parameter édité");
                return new ResponseEntity<Object>(res,HttpStatus.OK);
            }else{
                res.put("message","ce parametre n'existe pas");
                return new ResponseEntity<Object>(res,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> updateParameterFields(UUID id, Map<String, Object> fields) {
        Map<String, Object> res = new HashMap<>();
        try {
            Optional<Parameter> existingParameter=parameterRepository.findById(id);
            if(existingParameter.isPresent()){
                fields.forEach((key,value)->{
                    Field field= ReflectionUtils.findField(Parameter.class,key);
                    field.setAccessible(true);
                    ReflectionUtils.setField(field,existingParameter.get(),value);
                });
                Parameter parameter=parameterRepository.save(existingParameter.get());
                res.put("data",parameterMapper.parameterToParameterResponseDto(parameter));
                res.put("message","parameter édité");
                return new ResponseEntity<Object>(res,HttpStatus.OK);
            }else{
                res.put("message","ce parametre n'existe pas");
                return new ResponseEntity<Object>(res,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> deleteParameter(UUID id) {
        Map<String, Object> res = new HashMap<>();
        try {
            if(parameterRepository.existsById(id)){
                parameterRepository.deleteById(id);
                res.put("message","parameter supprimé");
                return new ResponseEntity<Object>(res, HttpStatus.OK);
            }else{
                res.put("message","ce parameter n'existe pas");
                return new ResponseEntity<Object>(res, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ParameterResponseDto> getAllPlafonds() {
        try {
            List<Parameter> listParameters = parameterRepository.findAll();
            return listParameters.stream()
                    .filter(parameter -> parameter.getLibelle().startsWith("PLAFOND_"))
                    .map(parameterMapper::parameterToParameterResponseDto)
                    .toList();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
