package com.api.divideai.event.web.controller;

import com.api.divideai.event.application.dto.AnalyticsStatsDto;
import com.api.divideai.event.application.dto.AverageLoadingTimeDto;
import com.api.divideai.event.application.dto.EventHistoryDto;
import com.api.divideai.event.application.dto.PagedEventResponseDto;
import com.api.divideai.event.application.dto.TotalCountDto;
import com.api.divideai.event.application.services.EventService;
import com.api.divideai.event.domain.collections.Event;
import com.api.divideai.event.domain.enums.EventType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAnalyticsStats() throws Exception {
        // Arrange
        AnalyticsStatsDto dto = new AnalyticsStatsDto(150.5, 25L, 50L);
        when(eventService.getAnalyticsStats()).thenReturn(dto);

        // Act & Assert
        mockMvc.perform(get("/event/analytics/stats"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.averageLoadingTime").value(150.5))
                .andExpect(jsonPath("$.loadingTimeUnit").value("milliseconds"))
                .andExpect(jsonPath("$.totalClicks").value(25))
                .andExpect(jsonPath("$.totalPageViews").value(50));
    }

    @Test
    void testGetAverageLoadingTime() throws Exception {
        // Arrange
        AverageLoadingTimeDto dto = new AverageLoadingTimeDto(150.5);
        when(eventService.getAverageLoadingTime()).thenReturn(dto);

        // Act & Assert
        mockMvc.perform(get("/event/analytics/average-loading-time"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.averageLoadingTime").value(150.5))
                .andExpect(jsonPath("$.unit").value("milliseconds"));
    }

    @Test
    void testGetTotalClicks() throws Exception {
        // Arrange
        TotalCountDto dto = new TotalCountDto(EventType.CLICK, 25L);
        when(eventService.getTotalClicks()).thenReturn(dto);

        // Act & Assert
        mockMvc.perform(get("/event/analytics/total-clicks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.eventType").value("CLICK"))
                .andExpect(jsonPath("$.totalCount").value(25));
    }

    @Test
    void testGetTotalPageViews() throws Exception {
        // Arrange
        TotalCountDto dto = new TotalCountDto(EventType.PAGE_VIEW, 50L);
        when(eventService.getTotalPageViews()).thenReturn(dto);

        // Act & Assert
        mockMvc.perform(get("/event/analytics/total-page-views"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.eventType").value("PAGE_VIEW"))
                .andExpect(jsonPath("$.totalCount").value(50));
    }

    @Test
    void testGetLoadingHistory() throws Exception {
        // Arrange
        Event event1 = createEvent("1", EventType.LOADING, 100L);
        Event event2 = createEvent("2", EventType.LOADING, 200L);
        List<Event> events = Arrays.asList(event1, event2);
        EventHistoryDto dto = new EventHistoryDto(EventType.LOADING, events);
        
        when(eventService.getLoadingHistory()).thenReturn(dto);

        // Act & Assert
        mockMvc.perform(get("/event/analytics/loading-history"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.eventType").value("LOADING"))
                .andExpect(jsonPath("$.count").value(2))
                .andExpect(jsonPath("$.recentEvents").isArray())
                .andExpect(jsonPath("$.recentEvents.length()").value(2));
    }

    @Test
    void testGetClickHistory() throws Exception {
        // Arrange
        Event event1 = createEvent("1", EventType.CLICK, 50L);
        List<Event> events = Arrays.asList(event1);
        EventHistoryDto dto = new EventHistoryDto(EventType.CLICK, events);
        
        when(eventService.getClickHistory()).thenReturn(dto);

        // Act & Assert
        mockMvc.perform(get("/event/analytics/click-history"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.eventType").value("CLICK"))
                .andExpect(jsonPath("$.count").value(1));
    }

    @Test
    void testGetPageViewHistory() throws Exception {
        // Arrange
        Event event1 = createEvent("1", EventType.PAGE_VIEW, 150L);
        List<Event> events = Arrays.asList(event1);
        EventHistoryDto dto = new EventHistoryDto(EventType.PAGE_VIEW, events);
        
        when(eventService.getPageViewHistory()).thenReturn(dto);

        // Act & Assert
        mockMvc.perform(get("/event/analytics/page-view-history"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.eventType").value("PAGE_VIEW"))
                .andExpect(jsonPath("$.count").value(1));
    }

    @Test
    void testFindAllPaginated() throws Exception {
        // Arrange
        Event event1 = createEvent("1", EventType.CLICK, 100L);
        Event event2 = createEvent("2", EventType.PAGE_VIEW, 200L);
        List<Event> events = Arrays.asList(event1, event2);

        PagedEventResponseDto pagedResponse = new PagedEventResponseDto(events, 0, 10, 2);
        when(eventService.findAllPaginated(0, 10)).thenReturn(pagedResponse);

        // Act & Assert
        mockMvc.perform(get("/event/paginated")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(10))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.first").value(true))
                .andExpect(jsonPath("$.last").value(true))
                .andExpect(jsonPath("$.hasNext").value(false))
                .andExpect(jsonPath("$.hasPrevious").value(false));
    }

    @Test
    void testFindAllPaginatedWithDefaultParams() throws Exception {
        // Arrange
        Event event1 = createEvent("1", EventType.CLICK, 100L);
        List<Event> events = Arrays.asList(event1);

        PagedEventResponseDto pagedResponse = new PagedEventResponseDto(events, 0, 10, 1);
        when(eventService.findAllPaginated(0, 10)).thenReturn(pagedResponse);

        // Act & Assert
        mockMvc.perform(get("/event/paginated"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(10));
    }

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
}
