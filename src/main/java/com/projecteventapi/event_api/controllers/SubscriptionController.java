package com.projecteventapi.event_api.controllers;

import com.projecteventapi.event_api.dto.ErrorMessage;
import com.projecteventapi.event_api.dto.SubscriptionResponse;
import com.projecteventapi.event_api.dto.UserDto;
import com.projecteventapi.event_api.services.SubscriptionService;
import com.projecteventapi.event_api.services.exceptions.EventNotFoundException;
import com.projecteventapi.event_api.services.exceptions.SubscriptionConflictException;
import com.projecteventapi.event_api.services.exceptions.UserIndicatorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/{prettyName}")
    public ResponseEntity<?> createSubscription(@PathVariable String prettyName, @RequestBody UserDto subscriber) {

        try {
            SubscriptionResponse response = subscriptionService.createNewSubscription(prettyName, subscriber);
            if (response != null){
                return ResponseEntity.ok(response);
            }

        } catch (EventNotFoundException | UserIndicatorNotFoundException e){
            return ResponseEntity.status(404).body(new ErrorMessage(e.getMessage()));

        } catch (SubscriptionConflictException e){
            return ResponseEntity.status(409).body(new ErrorMessage(e.getMessage()));
        }
        return ResponseEntity.badRequest().build();
    }
}
