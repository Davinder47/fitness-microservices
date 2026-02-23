package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "users") //To give custom name to the table, so there will be
// conflict with the internal user table if there is any inside the DB
@Data // To generate the Getters and Setters
public class User {
    //Defining the fields: Which you want to store from a user standpoint like:
    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private UserRole role = UserRole.User;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
