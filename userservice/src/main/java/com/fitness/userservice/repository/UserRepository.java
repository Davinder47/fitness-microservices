package com.fitness.userservice.repository;

import com.fitness.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // Existing method used for registration to prevent duplicate accounts
    boolean existsByEmail(String email);

    // NEW method needed for login: It fetches the full User object based on the email.
    // We use Optional because the user might not exist in the database.
    Optional<User> findByEmail(String email);
}