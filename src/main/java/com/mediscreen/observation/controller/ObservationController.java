package com.mediscreen.observation.controller;

import com.mediscreen.observation.model.Observation;
import com.mediscreen.observation.proxy.PatientProxy;
import com.mediscreen.observation.service.ObservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class ObservationController {

    private final ObservationService observationService;

    @Autowired
    public ObservationController(ObservationService observationService){
        this.observationService = observationService;
    }

    @GetMapping("/observationForm")
    public ModelAndView showHome(@RequestParam(required = false) String id) {

        String viewName = "observationForm";

        Map<String,Object> model = new HashMap<>();

        model.put("patients",observationService.getPatients());

        if(id != null) {
            Optional<Observation> patient = observationService.findById(id);

            if(!patient.isPresent())
                model.put("observation", new Observation());
            else
                model.put("observation", patient.get());
        }else
            model.put("observation", new Observation());

        return new ModelAndView(viewName,model);
    }

    @PostMapping("/observationForm")
    public ModelAndView submitObservation( Observation observation, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return new ModelAndView("observationForm");
        }

        observationService.addOrUpdateObservation(observation);

        RedirectView redirect = new RedirectView();
        redirect.setUrl("/observations");
        return new ModelAndView(redirect);
    }

    @GetMapping("/observations")
    public ModelAndView getObservation() {

        String viewName = "list";

        Map<String,Object> model = new HashMap<>();

        model.put("observations", observationService.findAll());

        return new ModelAndView(viewName,model);
    }

    @GetMapping("/observation/delete/{id}")
    public ModelAndView deleteObservation(@PathVariable("id") String id) {
        observationService.deleteById(id);

        RedirectView redirect = new RedirectView();
        redirect.setUrl("/observations");
        return new ModelAndView(redirect);
    }

}
