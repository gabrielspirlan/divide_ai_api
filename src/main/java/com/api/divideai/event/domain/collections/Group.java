package com.api.divideai.event.domain.collections;

import com.mongodb.lang.Nullable;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Group {

    @Id
    private String id;
    @NotBlank(message = "Obrigatório informar o nome do usuário")
    private String name;
    @NotBlank(message = "Obrigatório informar a descrição do grupo")
    private String description;
    @NotBlank(message = "Obrigatório informar ao menos um usuário como participante")
    private List<String> participants;
    @Nullable
    private String backgroundIconColor;

    public Group() {
    }


    public Group(String name, String description, List<String> participants, String backgroundIconColor) {
        this.name = name;
        this.description = description;
        this.participants = participants;
        this.backgroundIconColor = backgroundIconColor;
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
}
