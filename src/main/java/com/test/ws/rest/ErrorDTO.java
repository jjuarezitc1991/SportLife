package com.test.ws.rest;

import java.util.ArrayList;
import java.util.List;

public class ErrorDTO {

    private String description;

    private List<String> details = new ArrayList<String>();


    public ErrorDTO(String description) {
        super();
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    public void addDetail(String detail) {
        this.details.add(detail);
    }
    
}
