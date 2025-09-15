package com.api.divideai.event.application.dto;

public class AnalyticsStatsDto {
    
    private Double averageLoadingTime;
    private String loadingTimeUnit;
    private Long totalClicks;
    private Long totalPageViews;
    
    public AnalyticsStatsDto() {
        this.loadingTimeUnit = "milliseconds";
    }
    
    public AnalyticsStatsDto(Double averageLoadingTime, Long totalClicks, Long totalPageViews) {
        this.averageLoadingTime = averageLoadingTime;
        this.totalClicks = totalClicks;
        this.totalPageViews = totalPageViews;
        this.loadingTimeUnit = "milliseconds";
    }
    
    public Double getAverageLoadingTime() {
        return averageLoadingTime;
    }
    
    public void setAverageLoadingTime(Double averageLoadingTime) {
        this.averageLoadingTime = averageLoadingTime;
    }
    
    public String getLoadingTimeUnit() {
        return loadingTimeUnit;
    }
    
    public void setLoadingTimeUnit(String loadingTimeUnit) {
        this.loadingTimeUnit = loadingTimeUnit;
    }
    
    public Long getTotalClicks() {
        return totalClicks;
    }
    
    public void setTotalClicks(Long totalClicks) {
        this.totalClicks = totalClicks;
    }
    
    public Long getTotalPageViews() {
        return totalPageViews;
    }
    
    public void setTotalPageViews(Long totalPageViews) {
        this.totalPageViews = totalPageViews;
    }
}
