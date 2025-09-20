package com.api.divideai.event.application.dto;

public class MostClickedElementDto {
    private String elementId;
    private Long clickCount;
    private String variant;
    private String page;

    public MostClickedElementDto() {
    }

    public MostClickedElementDto(String elementId, Long clickCount, String variant, String page) {
        this.elementId = elementId;
        this.clickCount = clickCount;
        this.variant = variant;
        this.page = page;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public Long getClickCount() {
        return clickCount;
    }

    public void setClickCount(Long clickCount) {
        this.clickCount = clickCount;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
