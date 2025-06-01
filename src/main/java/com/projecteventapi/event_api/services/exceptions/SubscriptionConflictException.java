package com.projecteventapi.event_api.services.exceptions;

public class SubscriptionConflictException extends RuntimeException {
    public SubscriptionConflictException(String message){
        super(message);
    }
}
