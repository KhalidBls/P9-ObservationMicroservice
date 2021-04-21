package com.mediscreen.observation.repository;

import com.mediscreen.observation.model.Observation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObservationRepository extends MongoRepository<Observation,String> {

    List<Observation> findAllByIdPatient(Integer idPatient);
}
