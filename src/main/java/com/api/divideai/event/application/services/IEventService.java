package com.api.divideai.event.application.services;

import com.api.divideai.event.domain.collections.Event;

import java.util.List;

public interface IEventService {

    Event findById(String id);

    List<Event> findAll();

    Event create(Event event);

    String delete(String id);
}
