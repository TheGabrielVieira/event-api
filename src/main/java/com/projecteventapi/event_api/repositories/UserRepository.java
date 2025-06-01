package com.projecteventapi.event_api.repositories;

import com.projecteventapi.event_api.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findByUserEmail(String userEmail);
}

