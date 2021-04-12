package com.mediscreen.observation.service;

import com.mediscreen.observation.model.Observation;
import com.mediscreen.observation.dto.PatientDTO;
import com.mediscreen.observation.proxy.PatientProxy;
import com.mediscreen.observation.repository.ObservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObservationService {

    private final ObservationRepository observationRepository;
    private final PatientProxy patientProxy;

    ObservationService(ObservationRepository observationRepository, PatientProxy patientProxy){
        this.observationRepository = observationRepository;
        this.patientProxy = patientProxy;
    }

    public Optional<Observation> findById(String id) {
        return observationRepository.findById(id);
    }

    public void addOrUpdateObservation(Observation observationToSave) {
        Observation observation;
        if(!observationToSave.getId().equals("")) {
            observation = findById(observationToSave.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid observation id :"+ observationToSave.getId()));
            observation.setContent(observationToSave.getContent());
            observation.setIdPatient(observationToSave.getIdPatient());
            save(observation);
        }else{
            save(observationToSave);
        }
    }

    public Observation save(Observation observation) {
        return observationRepository.save(observation);
    }

    public List<Observation> findAll() {
        return observationRepository.findAll();
    }

    public void deleteById(String id) {
        observationRepository.deleteById(id);
    }

    public List<PatientDTO> getPatients() {
        return patientProxy.getPatients();
    }
}
