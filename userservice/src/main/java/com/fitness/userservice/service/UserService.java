package com.fitness.userservice.service;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

//Method where we will be registering the user into aur application
public class UserService {

    //Getting instance of repository
    @Autowired
    private UserRepository repository;

    public UserResponse register(RegisterRequest request) {
        //First we will create User object and set his things
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        repository.save(user);

        //Now we will save the user into our database,
        // we need repository layer for this i.e UserRepository(which is an interface)

    }
}
