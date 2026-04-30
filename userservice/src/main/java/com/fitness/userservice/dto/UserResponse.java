package com.fitness.userservice.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//This is a DTO object
//Here we will define what all fields we want to be part,
// like we want this particular class part
//Whatever response that will be seen by the user will be here
@Data // Generates Getters and Setters
@NoArgsConstructor // Generates the default constructor (no arguments)
@AllArgsConstructor // Generates the constructor with all 4 fields (Fixes the error)
@Builder
public class UserResponse {

    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
