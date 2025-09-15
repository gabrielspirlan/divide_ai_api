package com.api.divideai.event.application.services;

import com.api.divideai.event.application.dto.AnalyticsStatsDto;
import com.api.divideai.event.application.dto.AverageLoadingTimeDto;
import com.api.divideai.event.application.dto.EventHistoryDto;
import com.api.divideai.event.application.dto.PagedEventResponseDto;
import com.api.divideai.event.application.dto.TotalCountDto;
import com.api.divideai.event.domain.collections.Event;
import com.api.divideai.event.domain.enums.EventType;
import com.api.divideai.event.infrastructure.repositories.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    private Event createEvent(String id, EventType eventType, Long loading) {
        Event event = new Event();
        event.setId(id);
        event.setEventType(eventType);
        event.setLoading(loading);
        event.setElementId("test-element");
        event.setVariant("A");
        event.setPage("test-page");
        return event;
    }

    @Test
    void testGetAnalyticsStats() {
        // Arrange
        List<Event> loadingEvents = Arrays.asList(
            createEvent("1", EventType.LOADING, 100L),
            createEvent("2", EventType.LOADING, 200L)
        );

        when(eventRepository.findAllWithLoadingTime()).thenReturn(loadingEvents);
        when(eventRepository.countByEventType(EventType.CLICK)).thenReturn(15L);
        when(eventRepository.countByEventType(EventType.PAGE_VIEW)).thenReturn(25L);

        // Act
        AnalyticsStatsDto result = eventService.getAnalyticsStats();

        // Assert
        assertNotNull(result);
        assertEquals(150.0, result.getAverageLoadingTime()); // (100 + 200) / 2
        assertEquals("milliseconds", result.getLoadingTimeUnit());
        assertEquals(15L, result.getTotalClicks());
        assertEquals(25L, result.getTotalPageViews());
    }

    @Test
    void testGetAnalyticsStatsWithEmptyLoadingEvents() {
        // Arrange
        when(eventRepository.findAllWithLoadingTime()).thenReturn(Arrays.asList());
        when(eventRepository.countByEventType(EventType.CLICK)).thenReturn(10L);
        when(eventRepository.countByEventType(EventType.PAGE_VIEW)).thenReturn(20L);

        // Act
        AnalyticsStatsDto result = eventService.getAnalyticsStats();

        // Assert
        assertNotNull(result);
        assertEquals(0.0, result.getAverageLoadingTime());
        assertEquals("milliseconds", result.getLoadingTimeUnit());
        assertEquals(10L, result.getTotalClicks());
        assertEquals(20L, result.getTotalPageViews());
    }

    @Test
    void testGetAverageLoadingTime() {
        // Arrange
        List<Event> events = Arrays.asList(
            createEvent("1", EventType.LOADING, 100L),
            createEvent("2", EventType.LOADING, 200L),
            createEvent("3", EventType.LOADING, 300L)
        );
        
        when(eventRepository.findAllWithLoadingTime()).thenReturn(events);

        // Act
        AverageLoadingTimeDto result = eventService.getAverageLoadingTime();

        // Assert
        assertNotNull(result);
        assertEquals(200.0, result.getAverageLoadingTime());
        assertEquals("milliseconds", result.getUnit());
    }

    @Test
    void testGetAverageLoadingTimeWithEmptyList() {
        // Arrange
        when(eventRepository.findAllWithLoadingTime()).thenReturn(Arrays.asList());

        // Act
        AverageLoadingTimeDto result = eventService.getAverageLoadingTime();

        // Assert
        assertNotNull(result);
        assertEquals(0.0, result.getAverageLoadingTime());
    }

    @Test
    void testGetTotalClicks() {
        // Arrange
        when(eventRepository.countByEventType(EventType.CLICK)).thenReturn(5L);

        // Act
        TotalCountDto result = eventService.getTotalClicks();

        // Assert
        assertNotNull(result);
        assertEquals(EventType.CLICK, result.getEventType());
        assertEquals(5L, result.getTotalCount());
    }

    @Test
    void testGetTotalPageViews() {
        // Arrange
        when(eventRepository.countByEventType(EventType.PAGE_VIEW)).thenReturn(10L);

        // Act
        TotalCountDto result = eventService.getTotalPageViews();

        // Assert
        assertNotNull(result);
        assertEquals(EventType.PAGE_VIEW, result.getEventType());
        assertEquals(10L, result.getTotalCount());
    }

    @Test
    void testGetLoadingHistory() {
        // Arrange
        List<Event> events = Arrays.asList(
            createEvent("1", EventType.LOADING, 100L),
            createEvent("2", EventType.LOADING, 200L)
        );
        
        when(eventRepository.findByEventTypeOrderByIdDesc(eq(EventType.LOADING), any(Pageable.class)))
            .thenReturn(events);

        // Act
        EventHistoryDto result = eventService.getLoadingHistory();

        // Assert
        assertNotNull(result);
        assertEquals(EventType.LOADING, result.getEventType());
        assertEquals(2, result.getCount());
        assertEquals(2, result.getRecentEvents().size());
    }

    @Test
    void testGetClickHistory() {
        // Arrange
        List<Event> events = Arrays.asList(
            createEvent("1", EventType.CLICK, 50L),
            createEvent("2", EventType.CLICK, 75L)
        );
        
        when(eventRepository.findByEventTypeOrderByIdDesc(eq(EventType.CLICK), any(Pageable.class)))
            .thenReturn(events);

        // Act
        EventHistoryDto result = eventService.getClickHistory();

        // Assert
        assertNotNull(result);
        assertEquals(EventType.CLICK, result.getEventType());
        assertEquals(2, result.getCount());
        assertEquals(2, result.getRecentEvents().size());
    }

    @Test
    void testGetPageViewHistory() {
        // Arrange
        List<Event> events = Arrays.asList(
            createEvent("1", EventType.PAGE_VIEW, 150L),
            createEvent("2", EventType.PAGE_VIEW, 250L)
        );
        
        when(eventRepository.findByEventTypeOrderByIdDesc(eq(EventType.PAGE_VIEW), any(Pageable.class)))
            .thenReturn(events);

        // Act
        EventHistoryDto result = eventService.getPageViewHistory();

        // Assert
        assertNotNull(result);
        assertEquals(EventType.PAGE_VIEW, result.getEventType());
        assertEquals(2, result.getCount());
        assertEquals(2, result.getRecentEvents().size());
    }

    @Test
    void testFindAllPaginated() {
        // Arrange
        List<Event> events = Arrays.asList(
            createEvent("1", EventType.CLICK, 100L),
            createEvent("2", EventType.PAGE_VIEW, 200L)
        );

        Page<Event> eventPage = new PageImpl<>(events, PageRequest.of(0, 10), 2);
        when(eventRepository.findAll(any(Pageable.class))).thenReturn(eventPage);

        // Act
        PagedEventResponseDto result = eventService.findAllPaginated(0, 10);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(0, result.getPage());
        assertEquals(10, result.getSize());
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertTrue(result.isFirst());
        assertTrue(result.isLast());
        assertFalse(result.isHasNext());
        assertFalse(result.isHasPrevious());
    }

    @Test
    void testFindAllPaginatedWithMultiplePages() {
        // Arrange
        List<Event> events = Arrays.asList(
            createEvent("1", EventType.CLICK, 100L),
            createEvent("2", EventType.PAGE_VIEW, 200L)
        );

        Page<Event> eventPage = new PageImpl<>(events, PageRequest.of(1, 2), 5);
        when(eventRepository.findAll(any(Pageable.class))).thenReturn(eventPage);

        // Act
        PagedEventResponseDto result = eventService.findAllPaginated(1, 2);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(1, result.getPage());
        assertEquals(2, result.getSize());
        assertEquals(5, result.getTotalElements());
        assertEquals(3, result.getTotalPages());
        assertFalse(result.isFirst());
        assertFalse(result.isLast());
        assertTrue(result.isHasNext());
        assertTrue(result.isHasPrevious());
    }
}
