package com.mediscreen.observation.controller;

import com.mediscreen.observation.model.Observation;
import com.mediscreen.observation.service.ObservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class ObservationRestController {

    private final ObservationService observationService;

    @Autowired
    public ObservationRestController(ObservationService observationService){
        this.observationService = observationService;
    }


    @GetMapping("/rest/observation")
    public List<Observation> listAllobservations(){
        return observationService.findAll();
    }

    @GetMapping("/rest/observation/{id}")
    public Optional<Observation> getobservationById(@PathVariable String id){
        return observationService.findById(id);
    }

    @GetMapping("/rest/patient/observation/{id}")
    public List<Observation> getobservationByPatientId(@PathVariable Integer id){
        return observationService.findByPatientId(id);
    }

    @PutMapping("/rest/observation/update/{id}")
    public ResponseEntity<Observation> updateobservation(@PathVariable String id, @RequestBody  Observation observationDetails){
        Observation observation = observationService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid observation id :"+ id));

        observation.setContent(observationDetails.getContent());
        observation.setIdPatient(observationDetails.getIdPatient());

        observationService.save(observation);

        return  ResponseEntity.ok(observation);
    }

    @DeleteMapping("/rest/observation/delete/{id}")
    public void deleteobservation(@PathVariable String id){
        observationService.deleteById(id);
    }

    @PostMapping("/rest/observation/add")
    public ResponseEntity<Observation> addobservation(@RequestBody Observation observation){
        Observation observationSaved = observationService.save(observation);

        if(observationSaved == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/observation/{id}")
                .buildAndExpand(observationSaved.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
