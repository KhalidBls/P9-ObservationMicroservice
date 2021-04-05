package com.mediscreen.observation.repository;

import com.mediscreen.observation.model.Observation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObservationRepository extends MongoRepository<Observation,String> {
}
