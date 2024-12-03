package org.bakka.userservice.Services;

import jakarta.transaction.Transactional;
import org.bakka.userservice.Dtos.ActivitiesRequestDto;
import org.bakka.userservice.Dtos.ActivitiesResponseDto;
import org.bakka.userservice.Entities.Activities;
import org.bakka.userservice.Mappers.ActivitiesMapper;
import org.bakka.userservice.Repositories.ActivitiesRepository;
import org.bakka.userservice.Repositories.AdministrateurRepository;
import org.bakka.userservice.Repositories.ClientBakkaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class ActivitiesServiceImpl implements ActivitiesService{

    private final ActivitiesMapper activitiesMapper;
    private final ActivitiesRepository activitiesRepository;
    private final AdministrateurRepository administrateurRepository;
    private final ClientBakkaRepository clientBakkaRepository;

    public ActivitiesServiceImpl(ActivitiesMapper activitiesMapper, ActivitiesRepository activitiesRepository, AdministrateurRepository administrateurRepository, ClientBakkaRepository clientBakkaRepository) {
        this.activitiesMapper = activitiesMapper;
        this.activitiesRepository = activitiesRepository;
        this.administrateurRepository = administrateurRepository;
        this.clientBakkaRepository = clientBakkaRepository;
    }
    Map<String, Object> ac = new HashMap<>();
    @Override
    public ResponseEntity<?> addActivities(ActivitiesRequestDto activitiesRequestDto) {
        try{
            Activities activities = activitiesMapper.activitiesRequestDtoToActivities(activitiesRequestDto);
            Activities save = activitiesRepository.save(activities);
            ActivitiesResponseDto activitiesResponseDto = activitiesMapper.activitiesToActivitiesResponseDto(save);
            ac.put("message", "Ajout de l'activité avec succés");
            ac.put("data", activitiesResponseDto);
            return new ResponseEntity<Object>(ac, HttpStatus.CREATED);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> updateActivities(ActivitiesRequestDto activitiesRequestDto) {
        try {
            Activities activities = activitiesMapper.activitiesRequestDtoToActivities(activitiesRequestDto);
            Activities update = activitiesRepository.save(activities);
            ActivitiesResponseDto activitiesResponseDto = activitiesMapper.activitiesToActivitiesResponseDto(update);
            ac.put("message", "Modification faite avec succés");
            ac.put("data", activitiesResponseDto);
            return new ResponseEntity<Object>(ac, HttpStatus.OK);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> getActivities(UUID uuid) {
        try {
            if(activitiesRepository.existsById(uuid)){
                Activities activities = activitiesRepository.findById(uuid).get();
                ActivitiesResponseDto activitiesResponseDto = activitiesMapper.activitiesToActivitiesResponseDto(activities);
                return ResponseEntity.ok(activitiesResponseDto);
            }else{
                ac.put("message", "Aucune activité");
                return new ResponseEntity<Object>(ac, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> deleteActivities(UUID uuid) {
        try{
            if(activitiesRepository.existsById(uuid)){
                activitiesRepository.deleteById(uuid);
                ac.put("message", "Activité supprimé");
                return new ResponseEntity<Object>(ac, HttpStatus.OK);
            }else {
                ac.put("message", "Cette activité n'existe pas");
                return new ResponseEntity<Object>(ac, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ActivitiesResponseDto> getAllActivities() {
        try {
           List<Activities> activitiesList = activitiesRepository.findAll();
           return activitiesList.stream().map(activitiesMapper::activitiesToActivitiesResponseDto).toList();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
