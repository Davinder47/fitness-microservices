package com.fitness.userservice.dto;
import lombok.Data;

import java.time.LocalDateTime;

//This is a DTO object
//Here we will define what all fields we want to be part,
// like we want this particular class part
//Whatever response that will be seen by the user will be here
@Data//For generating the getters and setters
public class UserResponse {

    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
