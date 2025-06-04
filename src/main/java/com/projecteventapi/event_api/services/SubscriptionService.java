package com.projecteventapi.event_api.services;


import com.projecteventapi.event_api.dto.SubscriptionRankingByUser;
import com.projecteventapi.event_api.dto.SubscriptionRankingItem;
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
import java.util.stream.IntStream;

@Service
public class SubscriptionService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Transactional
    public SubscriptionResponse createNewSubscription(String eventName, UserDto subs, Integer userId) {

        User user = userRepository.findByUserEmail(subs.getUserEmail());

        if (user == null){
            user = new User();
            user.setUserName(subs.getUserName());
            user.setUserEmail(subs.getUserEmail());
            user = userRepository.save(user);
        }

        User indicator = null;
        if (userId != null) {
            indicator = userRepository.findById(userId).orElse(null);
            if (indicator == null){
                throw new UserIndicatorNotFoundException("User " + userId + " not found");
            }
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

    public List<SubscriptionRankingItem> getCompleteRanking(String prettyName){
        Event event = eventRepository.findByPrettyName(prettyName);
        if (event == null){
            throw new EventNotFoundException("Event " + prettyName + " not found");

        }
        return subscriptionRepository.generateRanking(event.getEventId());
    }

    public SubscriptionRankingByUser getRankingByUser(String prettyName, Integer userId){
        List<SubscriptionRankingItem> ranking = getCompleteRanking(prettyName);
        SubscriptionRankingItem item = ranking.stream().filter(id -> id.userID().equals(userId)).findFirst().orElse(null);
        if (item == null) {
            throw new UserIndicatorNotFoundException("There are no registrations with indication of the use " + userId);
        }

        int position = IntStream.range(0, ranking.size()).filter(pos -> ranking.get(pos).userID().equals(userId)).findFirst().getAsInt();

        return new SubscriptionRankingByUser(item, position+1);
    }
}
