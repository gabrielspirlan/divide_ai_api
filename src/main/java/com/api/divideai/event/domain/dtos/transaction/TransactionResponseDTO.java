package com.api.divideai.event.domain.dtos.transaction;

import java.util.Date;
import java.util.List;

public class TransactionResponseDTO {
    private String id;
    private String description;
    private Double value;
    private List<String> participants;
    private List<String> participantNames;
    private String group;
    private Date date;
    private Double valuePerPerson;

    public TransactionResponseDTO() {
    }

    public TransactionResponseDTO(String id, String description, Double value, List<String> participants, String group, Date date) {
        this.id = id;
        this.description = description;
        this.value = value;
        this.participants = participants;
        this.group = group;
        this.date = date;
    }

    public TransactionResponseDTO(String id, String description, Double value, List<String> participants, String group, Date date, Double valuePerPerson) {
        this.id = id;
        this.description = description;
        this.value = value;
        this.participants = participants;
        this.group = group;
        this.date = date;
        this.valuePerPerson = valuePerPerson;
    }

    public TransactionResponseDTO(String id, String description, Double value, List<String> participants, List<String> participantNames, String group, Date date, Double valuePerPerson) {
        this.id = id;
        this.description = description;
        this.value = value;
        this.participants = participants;
        this.participantNames = participantNames;
        this.group = group;
        this.date = date;
        this.valuePerPerson = valuePerPerson;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Double getValuePerPerson() {
        return valuePerPerson;
    }

    public void setValuePerPerson(Double valuePerPerson) {
        this.valuePerPerson = valuePerPerson;
    }

    public List<String> getParticipantNames() {
        return participantNames;
    }

    public void setParticipantNames(List<String> participantNames) {
        this.participantNames = participantNames;
    }
}
