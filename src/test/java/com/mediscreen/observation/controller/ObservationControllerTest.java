package com.mediscreen.observation.controller;

import com.mediscreen.observation.service.ObservationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest
@RunWith(SpringRunner.class)
public class ObservationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ObservationService observationService;

    @Test
    public void testShowObservationForm() throws Exception {

        mockMvc.perform(get("/observationForm"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("observationForm"))
                .andExpect(model().size(2))
                .andExpect(model().attributeExists("observation"))
                .andExpect(model().attributeExists("patients"));
    }

    @Test
    public void testSubmitObservationForm() throws Exception {
        mockMvc.perform(post("/observationForm")
                .param("idPatient", "3")
                .param("content", "le contenu"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/observations"));
    }

    @Test
    public void testGetObservations() throws Exception {

        mockMvc.perform(get("/observations"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("list"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("observations"));

    }

    @Test
    public void testDeleteObservations() throws Exception {

        mockMvc.perform(get("/observation/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/observations"));;
    }

}
