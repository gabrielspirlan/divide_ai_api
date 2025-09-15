package com.api.divideai.event.application.dto;

public class AverageLoadingTimeDto {
    
    private Double averageLoadingTime;
    private String unit;
    
    public AverageLoadingTimeDto() {
        this.unit = "milliseconds";
    }
    
    public AverageLoadingTimeDto(Double averageLoadingTime) {
        this.averageLoadingTime = averageLoadingTime;
        this.unit = "milliseconds";
    }
    
    public Double getAverageLoadingTime() {
        return averageLoadingTime;
    }
    
    public void setAverageLoadingTime(Double averageLoadingTime) {
        this.averageLoadingTime = averageLoadingTime;
    }
    
    public String getUnit() {
        return unit;
    }
    
    public void setUnit(String unit) {
        this.unit = unit;
    }
}
