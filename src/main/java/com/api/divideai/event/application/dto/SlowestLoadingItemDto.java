package com.api.divideai.event.application.dto;

public class SlowestLoadingItemDto {
    private String elementId;
    private String page;
    private String variant;
    private Long loadingTime;
    private String loadingTimeUnit;

    public SlowestLoadingItemDto() {
    }

    public SlowestLoadingItemDto(String elementId, String page, String variant, Long loadingTime) {
        this.elementId = elementId;
        this.page = page;
        this.variant = variant;
        this.loadingTime = loadingTime;
        this.loadingTimeUnit = "milliseconds";
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public Long getLoadingTime() {
        return loadingTime;
    }

    public void setLoadingTime(Long loadingTime) {
        this.loadingTime = loadingTime;
    }

    public String getLoadingTimeUnit() {
        return loadingTimeUnit;
    }

    public void setLoadingTimeUnit(String loadingTimeUnit) {
        this.loadingTimeUnit = loadingTimeUnit;
    }
}
