package com.api.divideai.event.application.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageDTO {

    private Integer page;
    private Integer size;
    private String sortBy;
    private String direction;

    public PageDTO() {
    }

    public PageDTO(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    public PageDTO(Integer page, Integer size, String sortBy, String direction) {
        this.page = page;
        this.size = size;
        this.sortBy = sortBy;
        this.direction = direction;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Pageable mapPage() {



        setPageAndSizeDefault();

        if(this.sortBy != null) {
            Sort sort = direction.equalsIgnoreCase("asc") ?
                    Sort.by(this.sortBy).ascending() :
                    Sort.by(this.sortBy).descending();
            return  PageRequest.of(this.page, this.size, sort);
        }

        return PageRequest.of(this.page, this.size);
    }

    public void sortByName() {
        if (this.getSortBy() == null || this.getSortBy().isEmpty()) {
            this.setSortBy("name");
            this.setDirection("asc");
        }
    }


    public void sortByDate() {
        if (this.getSortBy() == null || this.getSortBy().isEmpty()) {
            this.setSortBy("date");
            this.setDirection("desc");
        }
    }

    public void setPageAndSizeDefault () {
        if (this.getPage() == null) {
            this.setPage(0);
        }

        if (this.getSize() == null) {
            this.setSize(10);
        }
    }
}
