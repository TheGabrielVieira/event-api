package com.projecteventapi.event_api.repositories;

import com.projecteventapi.event_api.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    public User findByEmail(String email);
    public Optional<User> findById(Integer id);
}

