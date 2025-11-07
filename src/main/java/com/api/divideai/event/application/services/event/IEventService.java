package com.api.divideai.event.application.services.event;

import com.api.divideai.event.application.dto.AnalyticsStatsDto;
import com.api.divideai.event.application.dto.AverageLoadingTimeDto;
import com.api.divideai.event.application.dto.EventHistoryDto;
import com.api.divideai.event.application.dto.MostAccessedPageDto;
import com.api.divideai.event.application.dto.MostClickedElementDto;
import com.api.divideai.event.application.dto.PagedEventResponseDto;
import com.api.divideai.event.application.dto.SlowestLoadingItemDto;
import com.api.divideai.event.application.dto.TotalCountDto;
import com.api.divideai.event.domain.collections.Event;

import java.util.List;

public interface IEventService {

    Event findById(String id);

    List<Event> findAll();

    PagedEventResponseDto findAllPaginated(int page, int size);

    Event create(Event event);

    String delete(String id);

    // Analytics methods
    AnalyticsStatsDto getAnalyticsStats();

    AverageLoadingTimeDto getAverageLoadingTime();

    TotalCountDto getTotalClicks();

    TotalCountDto getTotalPageViews();

    EventHistoryDto getLoadingHistory();

    EventHistoryDto getClickHistory();

    EventHistoryDto getPageViewHistory();

    // Novos métodos para os endpoints solicitados
    SlowestLoadingItemDto getSlowestLoadingItem();

    MostClickedElementDto getMostClickedElement();

    MostAccessedPageDto getMostAccessedPage();
}
