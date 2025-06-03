package com.projecteventapi.event_api.repositories;

import com.projecteventapi.event_api.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUserEmail(String userEmail);
}

