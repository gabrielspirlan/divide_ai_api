package com.api.divideai.event.domain.dtos.group;

import java.util.List;

public class GroupRequestDTO {

    private String name;
    private String description;
    private List<String> participants;
    private String backgroundIconColor;

    public GroupRequestDTO() {
    }

    public GroupRequestDTO(String name, String description, List<String> participants, String backgroundIconColor) {
        this.name = name;
        this.description = description;
        this.participants = participants;
        this.backgroundIconColor = backgroundIconColor;
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
}
