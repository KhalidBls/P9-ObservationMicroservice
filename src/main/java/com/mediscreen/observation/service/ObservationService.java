package com.mediscreen.observation.service;

import com.mediscreen.observation.model.Observation;
import com.mediscreen.observation.repository.ObservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObservationService {

    private final ObservationRepository observationRepository;

    ObservationService(ObservationRepository observationRepository){
        this.observationRepository = observationRepository;
    }

    public Optional<Observation> findById(String id) {
        return observationRepository.findById(id);
    }

    public void addOrUpdateObservation(Observation observationToSave) {
        Observation observation = null;

        if(observationToSave.getId() != null) {
            observation = findById(observationToSave.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid observation id :"+ observationToSave.getId()));
            observation.setContent(observationToSave.getContent());
            observation.setIdPatient(observationToSave.getIdPatient());
            save(observation);
        }else{
            save(observationToSave);
        }
    }

    private void save(Observation observation) {
        observationRepository.save(observation);
    }

    public List<Observation> findAll() {
        return observationRepository.findAll();
    }

    public void deleteById(String id) {
        observationRepository.deleteById(id);
    }
}
