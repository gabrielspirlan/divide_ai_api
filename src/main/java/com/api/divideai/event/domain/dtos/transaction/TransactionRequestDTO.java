package com.api.divideai.event.domain.dtos.transaction;

import java.util.Date;
import java.util.List;

public class TransactionRequestDTO {

    private String description;
    private Double value;
    private List<String> participants;
    private String group;
    private Date date;


    public TransactionRequestDTO() {
    }

    public TransactionRequestDTO(String description, Double value, List<String> participants, String group, Date date) {
        this.description = description;
        this.value = value;
        this.participants = participants;
        this.group = group;
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
