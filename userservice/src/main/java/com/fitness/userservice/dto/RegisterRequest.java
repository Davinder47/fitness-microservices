package com.fitness.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

//Here we will define as to what all we want to accept from the user,
//as a part of the request
//The @Valid annotation which we defined in Controller is being used here,
// like in email and password. When request hits this controller, it will
// be validated
@Data//For getters and setters
public class RegisterRequest {
    //To define user we need:

    //In case Validation fails then print(@Valid is used here):
    @NotBlank(message = "Email is Required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is Required")
    @Size(min = 6, message = "Password must have atleast 6 characters")
    private String password;

    private String firstName;
    private String lastName;
}
