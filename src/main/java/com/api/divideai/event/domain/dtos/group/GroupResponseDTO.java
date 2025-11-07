package com.api.divideai.event.domain.dtos.group;

import org.springframework.data.annotation.Id;

import java.util.List;

public class GroupResponseDTO {

    private String id;
    private String name;
    private String description;
    private Double totalTransactions;
    private List<String> participants;
    private List<String> participantNames;
    private String backgroundIconColor;

    public GroupResponseDTO() {
    }


    public GroupResponseDTO(String id, String name, String description, Double totalTransactions, List<String> participants, String backgroundIconColor) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.totalTransactions = totalTransactions;
        this.participants = participants;
        this.backgroundIconColor = backgroundIconColor;
    }

    public GroupResponseDTO(String id, String name, String description, Double totalTransactions, List<String> participants, List<String> participantNames, String backgroundIconColor) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.totalTransactions = totalTransactions;
        this.participants = participants;
        this.participantNames = participantNames;
        this.backgroundIconColor = backgroundIconColor;
    }

    public Double getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(Double totalTransactions) {
        this.totalTransactions = totalTransactions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public String getBackgroundIconColor() {
        return backgroundIconColor;
    }

    public void setBackgroundIconColor(String backgroundIconColor) {
        this.backgroundIconColor = backgroundIconColor;
    }

    public List<String> getParticipantNames() {
        return participantNames;
    }

    public void setParticipantNames(List<String> participantNames) {
        this.participantNames = participantNames;
    }
}
