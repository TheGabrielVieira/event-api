package com.projecteventapi.event_api.controllers;

import com.projecteventapi.event_api.dto.EventDto;
import com.projecteventapi.event_api.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping
    public ResponseEntity<EventDto> save(@RequestBody EventDto eventDto) {
        eventDto = eventService.saveEvent(eventDto);
        return ResponseEntity.ok(eventDto);
    }

    @GetMapping("/{prettyName}")
    public ResponseEntity<EventDto> getEventByPrettyName(@PathVariable String prettyName) {
        EventDto eventDto = eventService.getEventByPrettyName(prettyName);
        if (eventDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(eventDto);
    }

    @GetMapping
    public ResponseEntity<List<EventDto>> getAllEvents() {
        List<EventDto> events = eventService.getAllEvents();
        if(events.size() <= 0){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(events);
    }
}