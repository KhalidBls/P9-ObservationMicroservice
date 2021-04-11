package com.mediscreen.observation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.observation.model.Observation;
import com.mediscreen.observation.service.ObservationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ObservationRestController.class)
@RunWith(SpringRunner.class)
public class ObservationRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ObservationService observationService;

    @Test
    public void testCreateObservation() throws Exception {
        //Given
        Observation observation;
        ObjectMapper mapper = new ObjectMapper();
        String observationJSON = "{\n" +
                "        \"content\": \"voici le contenu\",\n" +
                "        \"idPatient\": \"6\"\n" +
                "    }";
        observation = mapper.readValue(observationJSON, Observation.class);

        //When
        when(observationService.save(any(Observation.class))).thenReturn(observation);

        //Then
        mockMvc.perform(post("/rest/observation/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(observationJSON))
                .andExpect(status().is(201));

        assertTrue(observation.getIdPatient() == 6);
    }

    @Test
    public void testGetObservationById() throws Exception {

        //ARRANGE
        Observation observation = new Observation("LE CONTENU",99);
        observation.setId("iddenotreobjet123");

        //ACT
        when(observationService.findById("iddenotreobjet123")).thenReturn(java.util.Optional.of(observation));

        //ASSERT
        mockMvc.perform(get("/rest/observation/iddenotreobjet123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", is(observation.getContent())))
                .andExpect(jsonPath("$.idPatient", is(observation.getIdPatient())));

        verify(observationService,times(1)).findById(observation.getId());
    }

    @Test
    public void testUpdateObservation() throws Exception {
        //Given
        Observation observation;
        ObjectMapper mapper = new ObjectMapper();
        String observationJSON = "{\n" +
                "        \"content\": \"voici le contenu\",\n" +
                "        \"idPatient\": \"6\"\n" +
                "    }";
        observation = mapper.readValue(observationJSON, Observation.class);
        observation.setId("iddenotreobjet123");

        //WHEN
        when(observationService.findById("iddenotreobjet123")).thenReturn(java.util.Optional.of(observation));
        when(observationService.save(observation)).thenReturn(observation);

        //THEN
        mockMvc.perform(put("/rest/observation/update/iddenotreobjet123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(observationJSON))
                .andExpect(status().is(200));

        verify(observationService,times(1)).findById(observation.getId());
        verify(observationService,times(1)).save(observation);
        assertTrue(observation.getContent().equals("voici le contenu"));
    }

    @Test
    public void testDeleteObservation() throws Exception {
        //GIVEN
        Observation observation = new Observation("LE CONTENU",99);
        observation.setId("iddenotreobjet123");

        //WHEN
        doNothing().when(observationService).deleteById("iddenotreobjet123");
        //THEN
        mockMvc.perform(delete("/rest/observation/delete/iddenotreobjet123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        verify(observationService,times(1)).deleteById(observation.getId());
    }
    
}
