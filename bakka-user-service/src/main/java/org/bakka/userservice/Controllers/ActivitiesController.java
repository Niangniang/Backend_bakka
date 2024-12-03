package org.bakka.userservice.Controllers;

import org.bakka.userservice.Dtos.ActivitiesRequestDto;
import org.bakka.userservice.Dtos.ActivitiesResponseDto;
import org.bakka.userservice.Repositories.ActivitiesRepository;
import org.bakka.userservice.Services.ActivitiesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/activities")
public class ActivitiesController {
    private final ActivitiesRepository activitiesRepository;
    private final ActivitiesService activitiesService;

    public ActivitiesController(ActivitiesRepository activitiesRepository, ActivitiesService activitiesService) {
        this.activitiesRepository = activitiesRepository;
        this.activitiesService = activitiesService;
    }
    @PostMapping("/add")
    public ResponseEntity<?> addActivities(@RequestBody ActivitiesRequestDto activitiesRequestDto){
        return activitiesService.addActivities(activitiesRequestDto);
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateActivities(@RequestBody ActivitiesRequestDto activitiesRequestDto){
        return activitiesService.updateActivities(activitiesRequestDto);
    }
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteActivities(@PathVariable("uuid")UUID uuid){
        return activitiesService.deleteActivities(uuid);
    }
    @GetMapping("/all")
    public List<ActivitiesResponseDto> getAllActivities(){
        return activitiesService.getAllActivities();
    }
    @GetMapping("/{uuid}")
    public ResponseEntity<?> getActivities(@PathVariable("uuid") UUID uuid){
        return activitiesService.getActivities(uuid);
    }
}
