package com.api.divideai.event.domain.collections;


import com.api.divideai.event.domain.enums.EventType;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "event")
public class Event {

    @Id
    private String id;
    @NotBlank(message = "Deve informar o id do elemento acionado")
    private String elementId;
    @NotBlank(message = "Variação do teste deve ser informado, ex: A,B")
    private String variant;
    @NotBlank(message = "Tipo do evento deve ser informado")
    private EventType eventType;
    @NotBlank(message = "Página deve ser informada")
    private String page;
    private Long loading;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Long getLoading() {
        return loading;
    }

    public void setLoading(Long loading) {
        this.loading = loading;
    }
}
