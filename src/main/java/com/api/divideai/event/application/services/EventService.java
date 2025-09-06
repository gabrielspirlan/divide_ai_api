package com.api.divideai.event.application.services;

import com.api.divideai.event.domain.collections.Event;
import com.api.divideai.event.infrastructure.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}
