package com.api.divideai.event.application.dto;

import com.api.divideai.event.domain.enums.EventType;

public class TotalCountDto {
    
    private EventType eventType;
    private Long totalCount;
    
    public TotalCountDto() {}
    
    public TotalCountDto(EventType eventType, Long totalCount) {
        this.eventType = eventType;
        this.totalCount = totalCount;
    }
    
    public EventType getEventType() {
        return eventType;
    }
    
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
    
    public Long getTotalCount() {
        return totalCount;
    }
    
    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}
