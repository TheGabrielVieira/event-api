package com.projecteventapi.event_api.controllers;

import com.projecteventapi.event_api.dto.ErrorMessage;
import com.projecteventapi.event_api.dto.SubscriptionRankingItem;
import com.projecteventapi.event_api.dto.SubscriptionResponse;
import com.projecteventapi.event_api.dto.UserDto;
import com.projecteventapi.event_api.services.SubscriptionService;
import com.projecteventapi.event_api.services.exceptions.EventNotFoundException;
import com.projecteventapi.event_api.services.exceptions.SubscriptionConflictException;
import com.projecteventapi.event_api.services.exceptions.UserIndicatorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping({"/{prettyName}", "/{prettyName}/{userId}"})
    public ResponseEntity<?> createSubscription(@PathVariable String prettyName, @RequestBody UserDto subscriber, @PathVariable(required = false) Integer userId) {

        try {
            SubscriptionResponse response = subscriptionService.createNewSubscription(prettyName, subscriber, userId);
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

    @GetMapping("/{prettyName}/ranking")
    public ResponseEntity<?> generateRankingByEvent(@PathVariable String prettyName){
        try {
            List<SubscriptionRankingItem> ranking = subscriptionService.getCompleteRanking(prettyName).subList(0, 3);
            return ResponseEntity.ok(ranking);
        } catch (EventNotFoundException e) {
            return ResponseEntity.status(404).body(new ErrorMessage(e.getMessage()));
        }
    }

    @GetMapping("/{prettyName}/ranking/{userId}")
    public ResponseEntity<?> generateRankingByUserAndEvent(@PathVariable String prettyName, Integer userId){
        try {
            return ResponseEntity.ok(subscriptionService.getRankingByUser(prettyName, userId));
        } catch (Exception e) {
            return ResponseEntity.status(404).body((new ErrorMessage(e.getMessage())));
        }
    }
}
