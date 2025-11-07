package com.api.divideai.event.domain.collections;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.print.DocFlavor;
import java.util.Date;
import java.util.List;

@Document
public class Transaction {
    @Id
    private String id;
    @NotBlank(message = "Obrigatório informar a descrição da transação")
    private String description;
    @NotBlank(message = "Obrigatório informar o valor")
    private Double value;
    @NotBlank(message = "Obrigatório informar ao menos um usuário como participante")
    private List<String> participants;
    @NotBlank(message = "Obrigatório informar o grupo da transação")
    private String group;
    @NotBlank(message = "Obrigatório informar a data da transação")
    private Date date;

    public Transaction() {
    }

    public Transaction(String description, Double value, List<String> participants, String group, Date date) {
        this.description = description;
        this.value = value;
        this.participants = participants;
        this.group = group;
        this.date = date;
    }

    public Transaction(String id, String description, Double value, List<String> participants, String group, Date date) {
        this.id = id;
        this.description = description;
        this.value = value;
        this.participants = participants;
        this.group = group;
        this.date = date;
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
}
