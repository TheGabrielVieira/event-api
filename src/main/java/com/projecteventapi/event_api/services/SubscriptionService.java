package com.projecteventapi.event_api.services;


import com.projecteventapi.event_api.dto.SubscriptionResponse;
import com.projecteventapi.event_api.dto.UserDto;
import com.projecteventapi.event_api.entities.Event;
import com.projecteventapi.event_api.entities.Subscription;
import com.projecteventapi.event_api.entities.User;
import com.projecteventapi.event_api.repositories.EventRepository;
import com.projecteventapi.event_api.repositories.SubscriptionRepository;
import com.projecteventapi.event_api.repositories.UserRepository;
import com.projecteventapi.event_api.services.exceptions.EventNotFoundException;
import com.projecteventapi.event_api.services.exceptions.SubscriptionConflictException;
import com.projecteventapi.event_api.services.exceptions.UserIndicatorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Transactional
    public SubscriptionResponse createNewSubscription(String eventName, UserDto subs, Integer userID) {

        User user = new User();
        user.setUserName(subs.getUserName());
        user.setUserEmail(subs.getUserEmail());

        List<User> users = userRepository.findByUserEmail(user.getUserEmail());

        if(users.isEmpty()){
            user = userRepository.save(user);
        }

        User indicator = userRepository.findById(userID).orElse(null);
        if (indicator == null) {
            throw new UserIndicatorNotFoundException("User " + userID + " not found");
        }

        Event event = eventRepository.findByPrettyName(eventName);
        if (event == null) {
            throw new EventNotFoundException("Event " + eventName + " not found");
        }

        Subscription subscription = new Subscription();
        subscription.setEvent(event);
        subscription.setSubscriber(user);
        subscription.setIndication(indicator);

        Subscription tmpSubscription = subscriptionRepository.findByEventAndSubscriber(event, user);
        if (tmpSubscription != null) {
            throw new SubscriptionConflictException("Subscription already exists for user " + user.getUserName() + " in event " + eventName);
        }

        subscription = subscriptionRepository.save(subscription);

        String link = "https://eventlorem.com/" + subscription.getEvent().getPrettyName() + "/" + subscription.getSubscriber().getUserID();

        return new SubscriptionResponse(subscription.getSubscriptionNumber(), link);

    }
}
