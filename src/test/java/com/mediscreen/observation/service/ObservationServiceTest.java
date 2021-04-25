package com.mediscreen.observation.service;

import com.mediscreen.observation.model.Observation;
import com.mediscreen.observation.proxy.PatientProxy;
import com.mediscreen.observation.repository.ObservationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ObservationServiceTest {

    @Mock
    private ObservationRepository observationRepository;

    @Mock
    private PatientProxy patientProxy;

    @InjectMocks
    private ObservationService observationService;

    @Test
    public void testAddOrUpdateObservationShouldUpdate(){
        //GIVEN
        Observation observation = new Observation();
        observation.setId("idobservation123");
        observation.setContent("Le patient est ok");
        observation.setIdPatient(4);

        when(observationRepository.findById("idobservation123")).thenReturn(java.util.Optional.of(observation));
        when(observationRepository.save(observation)).thenReturn(observation);

        //WHEN
        observationService.addOrUpdateObservation(observation);

        //THEN
        verify(observationRepository,times(1)).save(observation);
        verify(observationRepository,times(1)).findById("idobservation123");
    }

    @Test
    public void testAddOrUpdateObservationShouldAdd(){
        //GIVEN
        Observation observation = new Observation();
        observation.setId("");
        observation.setContent("Le patient est ok");
        observation.setIdPatient(4);

        when(observationRepository.findById("idobservation123")).thenReturn(java.util.Optional.of(observation));
        when(observationRepository.save(observation)).thenReturn(observation);

        //WHEN
        observationService.addOrUpdateObservation(observation);

        //THEN
        verify(observationRepository,times(1)).save(observation);
        verify(observationRepository,times(0)).findById(anyString());
    }

}
