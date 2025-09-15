package com.api.divideai.event.web.controller;

import com.api.divideai.event.application.dto.AnalyticsStatsDto;
import com.api.divideai.event.application.dto.AverageLoadingTimeDto;
import com.api.divideai.event.application.dto.EventHistoryDto;
import com.api.divideai.event.application.dto.PagedEventResponseDto;
import com.api.divideai.event.application.dto.TotalCountDto;
import com.api.divideai.event.application.services.EventService;
import com.api.divideai.event.domain.collections.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping
    private ResponseEntity<List<Event>> findAll () {
        return new ResponseEntity<List<Event>>(eventService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/paginated")
    private ResponseEntity<PagedEventResponseDto> findAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PagedEventResponseDto result = eventService.findAllPaginated(page, size);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Event> findById (@PathVariable String id) {
        return new ResponseEntity<Event>(eventService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<Event> create (@RequestBody Event event) {
        return new ResponseEntity<Event>(eventService.create(event), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> delete (@PathVariable String id) {
        return new ResponseEntity<String>(eventService.delete(id), HttpStatus.NO_CONTENT);
    }

    // Analytics endpoints

    @GetMapping("/analytics/stats")
    public ResponseEntity<AnalyticsStatsDto> getAnalyticsStats() {
        AnalyticsStatsDto result = eventService.getAnalyticsStats();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/analytics/average-loading-time")
    public ResponseEntity<AverageLoadingTimeDto> getAverageLoadingTime() {
        AverageLoadingTimeDto result = eventService.getAverageLoadingTime();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/analytics/total-clicks")
    public ResponseEntity<TotalCountDto> getTotalClicks() {
        TotalCountDto result = eventService.getTotalClicks();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/analytics/total-page-views")
    public ResponseEntity<TotalCountDto> getTotalPageViews() {
        TotalCountDto result = eventService.getTotalPageViews();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/analytics/loading-history")
    public ResponseEntity<EventHistoryDto> getLoadingHistory() {
        EventHistoryDto result = eventService.getLoadingHistory();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/analytics/click-history")
    public ResponseEntity<EventHistoryDto> getClickHistory() {
        EventHistoryDto result = eventService.getClickHistory();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/analytics/page-view-history")
    public ResponseEntity<EventHistoryDto> getPageViewHistory() {
        EventHistoryDto result = eventService.getPageViewHistory();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
