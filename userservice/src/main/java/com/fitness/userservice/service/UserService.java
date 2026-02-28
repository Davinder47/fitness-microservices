package com.fitness.userservice.service;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    //Getting instance of repository
    @Autowired
    private UserRepository repository;


    //Method where we will be registering the user into aur application
    public UserResponse register(RegisterRequest request) {

        //Adding some Validations
        if (repository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already exists");
        }

        //First we will create User object and set his things
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        //After registration, we need return the user response, below will do this
        User savedUser = repository.save(user);
        UserResponse userResponse = new UserResponse();
        userResponse.setId(savedUser.getId());
        userResponse.setPassword(savedUser.getPassword());
        userResponse.setEmail(savedUser.getEmail());
        userResponse.setFirstName(savedUser.getFirstName());
        userResponse.setLastName(savedUser.getLastName());
        userResponse.setCreatedAt(savedUser.getCreatedAt());
        userResponse.setCreatedAt(savedUser.getUpdatedAt());
        return userResponse;

        //Now we will save the user into our database,
        // we need repository layer for this i.e UserRepository(which is an interface)

    }
}
