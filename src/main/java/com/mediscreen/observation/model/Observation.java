package com.mediscreen.observation.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Observation {

    @Id
    private String id;
    @Field
    private String content;
    @Field
    private Integer idPatient;

    public Observation(){}

    public Observation(String content, Integer idPatient) {
        this.content = content;
        this.idPatient = idPatient;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(Integer idPatient) {
        this.idPatient = idPatient;
    }
}
