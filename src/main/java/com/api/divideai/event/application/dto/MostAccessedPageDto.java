package com.api.divideai.event.application.dto;

public class MostAccessedPageDto {
    private String page;
    private Long accessCount;
    private String variant;

    public MostAccessedPageDto() {
    }

    public MostAccessedPageDto(String page, Long accessCount, String variant) {
        this.page = page;
        this.accessCount = accessCount;
        this.variant = variant;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Long getAccessCount() {
        return accessCount;
    }

    public void setAccessCount(Long accessCount) {
        this.accessCount = accessCount;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }
}
