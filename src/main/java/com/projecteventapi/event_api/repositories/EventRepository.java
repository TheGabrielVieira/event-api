package com.projecteventapi.event_api.repositories;

import com.projecteventapi.event_api.entities.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Integer> {
    public Event findByPrettyName(String prettyName);
}
