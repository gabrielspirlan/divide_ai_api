package com.api.divideai.event.application.services;

import com.api.divideai.event.application.dto.AnalyticsStatsDto;
import com.api.divideai.event.application.dto.AverageLoadingTimeDto;
import com.api.divideai.event.application.dto.EventHistoryDto;
import com.api.divideai.event.application.dto.PagedEventResponseDto;
import com.api.divideai.event.application.dto.TotalCountDto;
import com.api.divideai.event.domain.collections.Event;
import com.api.divideai.event.domain.enums.EventType;
import com.api.divideai.event.infrastructure.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EventService implements IEventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public Event findById(String id) {
        return eventRepository.findById(id).orElseThrow(() -> new RuntimeException("ID não encontrado"));
    }

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public PagedEventResponseDto findAllPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventPage = eventRepository.findAll(pageable);

        return new PagedEventResponseDto(
            eventPage.getContent(),
            eventPage.getNumber(),
            eventPage.getSize(),
            eventPage.getTotalElements()
        );
    }

    @Override
    public Event create(Event event) {
        event.setId(null);
        return eventRepository.save(event);
    }

    @Override
    public String delete(String id) {

        Event event = this.findById(id);
        eventRepository.delete(event);

        return "Event deleted";
    }

    @Override
    public AnalyticsStatsDto getAnalyticsStats() {
        // Calcular tempo médio de loading
        List<Event> eventsWithLoading = eventRepository.findAllWithLoadingTime();
        double averageLoading = 0.0;
        if (!eventsWithLoading.isEmpty()) {
            averageLoading = eventsWithLoading.stream()
                    .mapToLong(Event::getLoading)
                    .average()
                    .orElse(0.0);
        }

        // Contar cliques e page views
        Long totalClicks = eventRepository.countByEventType(EventType.CLICK);
        Long totalPageViews = eventRepository.countByEventType(EventType.PAGE_VIEW);

        return new AnalyticsStatsDto(averageLoading, totalClicks, totalPageViews);
    }

    @Override
    public AverageLoadingTimeDto getAverageLoadingTime() {
        List<Event> eventsWithLoading = eventRepository.findAllWithLoadingTime();

        if (eventsWithLoading.isEmpty()) {
            return new AverageLoadingTimeDto(0.0);
        }

        double average = eventsWithLoading.stream()
                .mapToLong(Event::getLoading)
                .average()
                .orElse(0.0);

        return new AverageLoadingTimeDto(average);
    }

    @Override
    public TotalCountDto getTotalClicks() {
        Long totalClicks = eventRepository.countByEventType(EventType.CLICK);
        return new TotalCountDto(EventType.CLICK, totalClicks);
    }

    @Override
    public TotalCountDto getTotalPageViews() {
        Long totalPageViews = eventRepository.countByEventType(EventType.PAGE_VIEW);
        return new TotalCountDto(EventType.PAGE_VIEW, totalPageViews);
    }

    @Override
    public EventHistoryDto getLoadingHistory() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Event> recentLoadingEvents = eventRepository.findByEventTypeOrderByIdDesc(EventType.LOADING, pageable);
        return new EventHistoryDto(EventType.LOADING, recentLoadingEvents);
    }

    @Override
    public EventHistoryDto getClickHistory() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Event> recentClickEvents = eventRepository.findByEventTypeOrderByIdDesc(EventType.CLICK, pageable);
        return new EventHistoryDto(EventType.CLICK, recentClickEvents);
    }

    @Override
    public EventHistoryDto getPageViewHistory() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Event> recentPageViewEvents = eventRepository.findByEventTypeOrderByIdDesc(EventType.PAGE_VIEW, pageable);
        return new EventHistoryDto(EventType.PAGE_VIEW, recentPageViewEvents);
    }
}
