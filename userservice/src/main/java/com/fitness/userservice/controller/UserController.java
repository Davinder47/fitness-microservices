package com.fitness.userservice.controller;

import com.fitness.userservice.dto.LoginRequest;
import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController //To mark this class as Controller
@RequestMapping("/api/users") //To use Mappings
public class UserController {

    @Autowired
    private UserService userService;

    //Defining endpoints here

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserProfile(@PathVariable String userId){
        return ResponseEntity.ok(userService.getUserProfile(userId));
        //UserResponse is a DTO(Data Transfer Object) which we created later,
        //which is going to represent the response i.e what should be the response
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request){
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("auth/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        // This calls the login method we added to UserService
        String token = userService.login(request);

        // We return the token as a simple string for now.
        // Later, your React app will store this in localStorage.
        return ResponseEntity.ok(token);
    }

    @GetMapping("/{userId}/validate")
    public ResponseEntity<Boolean> validateUser(@PathVariable String userId) {
        return ResponseEntity.ok(userService.existByUserId(userId));

    }
}
