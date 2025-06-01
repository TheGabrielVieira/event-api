package com.projecteventapi.event_api.services.exceptions;

public class UserIndicatorNotFoundException extends RuntimeException {
    public UserIndicatorNotFoundException(String message){
        super(message);
    }
}
