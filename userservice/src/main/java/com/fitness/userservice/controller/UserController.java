package com.fitness.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController //To mark this class as Controller
@RequestMapping("/api/users") //To use Mappings
public class UserController {

    //Defining endpoints here

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserProfile(@PathVariable String userId){
        return ResponseEntity.ok(userService.getUserProfile(userId));
        //UserResponse is a DTO(Data Transfer Object) which we created later,
        //which is going to represent the response
    }

    @GetMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(request.register(request));
    }
}
