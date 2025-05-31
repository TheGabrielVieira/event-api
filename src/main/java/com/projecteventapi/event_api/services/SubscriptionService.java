package com.projecteventapi.event_api.services;

import com.projecteventapi.event_api.dto.SubscriptionDto;
import com.projecteventapi.event_api.dto.UserDto;
import com.projecteventapi.event_api.entities.Event;
import com.projecteventapi.event_api.entities.Subscription;
import com.projecteventapi.event_api.entities.User;
import com.projecteventapi.event_api.repositories.EventRepository;
import com.projecteventapi.event_api.repositories.SubscriptionRepository;
import com.projecteventapi.event_api.repositories.UserRepository;
import com.projecteventapi.event_api.services.exceptions.EventNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public SubscriptionDto createNewSubscription(String eventName, UserDto subs) {

        User user = new User();
        user.setUserName(subs.getUserName());
        user.setUserEmail(subs.getUserEmail());
        user = userRepository.save(user);

        Event event = eventRepository.findByPrettyName(eventName);

        if (event == null) {
            throw new EventNotFoundException("Event " + eventName + " not found");
        }

        Subscription subscription = new Subscription();
        subscription.setEvent(event);
        subscription.setSubscriber(user);
        subscription = subscriptionRepository.save(subscription);

        return new SubscriptionDto(subscription);
    }
}
