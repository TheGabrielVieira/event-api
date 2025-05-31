package com.projecteventapi.event_api.dto;

import com.projecteventapi.event_api.entities.Event;
import com.projecteventapi.event_api.entities.Subscription;
import com.projecteventapi.event_api.entities.User;

public class SubscriptionDto {

    private Integer subscriptionNumber;
    private Event event;
    private User subscriber;
    private User indication;

    public SubscriptionDto() {
    }

    public SubscriptionDto(Integer subscriptionNumber, Event event, User subscriber, User indication) {
        this.subscriptionNumber = subscriptionNumber;
        this.event = event;
        this.subscriber = subscriber;
        this.indication = indication;
    }

    public SubscriptionDto(Subscription subscription) {
        subscriptionNumber = subscription.getSubscriptionNumber();
        event = subscription.getEvent();
        subscriber = subscription.getSubscriber();
        indication = subscription.getIndication();
    }

    public Integer getSubscriptionNumber() {
        return subscriptionNumber;
    }

    public void setSubscriptionNumber(Integer subscriptionNumber) {
        this.subscriptionNumber = subscriptionNumber;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(User subscriber) {
        this.subscriber = subscriber;
    }

    public User getIndication() {
        return indication;
    }

    public void setIndication(User indication) {
        this.indication = indication;
    }
}
