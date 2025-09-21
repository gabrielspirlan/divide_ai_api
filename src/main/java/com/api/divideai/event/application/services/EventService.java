package com.api.divideai.event.application.services;

import com.api.divideai.event.application.dto.AnalyticsStatsDto;
import com.api.divideai.event.application.dto.AverageLoadingTimeDto;
import com.api.divideai.event.application.dto.EventHistoryDto;
import com.api.divideai.event.application.dto.MostAccessedPageDto;
import com.api.divideai.event.application.dto.MostClickedElementDto;
import com.api.divideai.event.application.dto.PagedEventResponseDto;
import com.api.divideai.event.application.dto.SlowestLoadingItemDto;
import com.api.divideai.event.application.dto.TotalCountDto;
import com.api.divideai.event.domain.collections.Event;
import com.api.divideai.event.domain.enums.EventType;
import com.api.divideai.event.infrastructure.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
        event.setCreatedAt(LocalDateTime.now());
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
        List<Event> recentLoadingEvents = eventRepository.findByEventTypeOrderByCreatedAtDesc(EventType.LOADING, pageable);
        return new EventHistoryDto(EventType.LOADING, recentLoadingEvents);
    }

    @Override
    public EventHistoryDto getClickHistory() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Event> recentClickEvents = eventRepository.findByEventTypeOrderByCreatedAtDesc(EventType.CLICK, pageable);
        return new EventHistoryDto(EventType.CLICK, recentClickEvents);
    }

    @Override
    public EventHistoryDto getPageViewHistory() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Event> recentPageViewEvents = eventRepository.findByEventTypeOrderByCreatedAtDesc(EventType.PAGE_VIEW, pageable);
        return new EventHistoryDto(EventType.PAGE_VIEW, recentPageViewEvents);
    }

    @Override
    public SlowestLoadingItemDto getSlowestLoadingItem() {
        List<Event> eventsWithLoading = eventRepository.findAllWithLoadingTimeOrderByLoadingDesc();

        if (eventsWithLoading.isEmpty()) {
            return new SlowestLoadingItemDto("N/A", "N/A", "N/A", 0L);
        }

        // Encontrar o evento com maior tempo de loading
        Event slowestEvent = eventsWithLoading.stream()
                .max((e1, e2) -> Long.compare(e1.getLoading(), e2.getLoading()))
                .orElse(null);

        if (slowestEvent == null) {
            return new SlowestLoadingItemDto("N/A", "N/A", "N/A", 0L);
        }

        return new SlowestLoadingItemDto(
                slowestEvent.getElementId(),
                slowestEvent.getPage(),
                slowestEvent.getVariant(),
                slowestEvent.getLoading()
        );
    }

    @Override
    public MostClickedElementDto getMostClickedElement() {
        List<Event> clickEvents = eventRepository.findAllClickEvents();

        if (clickEvents.isEmpty()) {
            return new MostClickedElementDto("N/A", 0L, "N/A", "N/A");
        }

        // Agrupar por elementId e contar
        Map<String, List<Event>> groupedByElement = clickEvents.stream()
                .collect(Collectors.groupingBy(Event::getElementId));

        // Encontrar o elemento com mais cliques
        String mostClickedElementId = groupedByElement.entrySet().stream()
                .max(Map.Entry.comparingByValue((list1, list2) -> Integer.compare(list1.size(), list2.size())))
                .map(Map.Entry::getKey)
                .orElse("N/A");

        if ("N/A".equals(mostClickedElementId)) {
            return new MostClickedElementDto("N/A", 0L, "N/A", "N/A");
        }

        List<Event> mostClickedEvents = groupedByElement.get(mostClickedElementId);
        Event sampleEvent = mostClickedEvents.get(0);

        return new MostClickedElementDto(
                mostClickedElementId,
                (long) mostClickedEvents.size(),
                sampleEvent.getVariant(),
                sampleEvent.getPage()
        );
    }

    @Override
    public MostAccessedPageDto getMostAccessedPage() {
        List<Event> pageViewEvents = eventRepository.findAllPageViewEvents();

        if (pageViewEvents.isEmpty()) {
            return new MostAccessedPageDto("N/A", 0L, "N/A");
        }

        // Agrupar por página e contar
        Map<String, List<Event>> groupedByPage = pageViewEvents.stream()
                .collect(Collectors.groupingBy(Event::getPage));

        // Encontrar a página com mais acessos
        String mostAccessedPage = groupedByPage.entrySet().stream()
                .max(Map.Entry.comparingByValue((list1, list2) -> Integer.compare(list1.size(), list2.size())))
                .map(Map.Entry::getKey)
                .orElse("N/A");

        if ("N/A".equals(mostAccessedPage)) {
            return new MostAccessedPageDto("N/A", 0L, "N/A");
        }

        List<Event> mostAccessedEvents = groupedByPage.get(mostAccessedPage);
        Event sampleEvent = mostAccessedEvents.get(0);

        return new MostAccessedPageDto(
                mostAccessedPage,
                (long) mostAccessedEvents.size(),
                sampleEvent.getVariant()
        );
    }
}
