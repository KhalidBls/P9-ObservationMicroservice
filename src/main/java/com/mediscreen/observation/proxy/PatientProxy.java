package com.mediscreen.observation.proxy;

import com.mediscreen.observation.model.PatientDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PatientProxy {

    public List<PatientDTO> getPatients() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<PatientDTO>> responseEntity =
                restTemplate.exchange("http://localhost:8081/rest/patient", HttpMethod.GET, null, new ParameterizedTypeReference<List<PatientDTO>>() {
                });
        return responseEntity.getBody();
    }

}
