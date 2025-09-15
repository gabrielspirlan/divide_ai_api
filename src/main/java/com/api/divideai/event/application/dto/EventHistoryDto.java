package com.api.divideai.event.application.dto;

import com.api.divideai.event.domain.collections.Event;
import com.api.divideai.event.domain.enums.EventType;

import java.util.List;

public class EventHistoryDto {
    
    private EventType eventType;
    private List<Event> recentEvents;
    private Integer count;
    
    public EventHistoryDto() {}
    
    public EventHistoryDto(EventType eventType, List<Event> recentEvents) {
        this.eventType = eventType;
        this.recentEvents = recentEvents;
        this.count = recentEvents != null ? recentEvents.size() : 0;
    }
    
    public EventType getEventType() {
        return eventType;
    }
    
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
    
    public List<Event> getRecentEvents() {
        return recentEvents;
    }
    
    public void setRecentEvents(List<Event> recentEvents) {
        this.recentEvents = recentEvents;
        this.count = recentEvents != null ? recentEvents.size() : 0;
    }
    
    public Integer getCount() {
        return count;
    }
    
    public void setCount(Integer count) {
        this.count = count;
    }
}
