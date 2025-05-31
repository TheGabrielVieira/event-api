package com.projecteventapi.event_api.controllers;

import com.projecteventapi.event_api.dto.SubscriptionDto;
import com.projecteventapi.event_api.dto.UserDto;
import com.projecteventapi.event_api.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/{prettyName}")
    public ResponseEntity<SubscriptionDto> createSubscription(@PathVariable String prettyName, @RequestBody UserDto subscriber ) {
        SubscriptionDto subs = subscriptionService.createNewSubscription(prettyName, subscriber);

        if( subs == null ) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(subs);



    }
}
