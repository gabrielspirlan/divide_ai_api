package com.api.divideai.event.web.controller;

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


}
