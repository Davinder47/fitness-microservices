package com.example.activityservice;

import com.example.activityservice.model.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//This repository will help us to save the data into the DB
//MongoRepository is an interface which already exists
//Activity is the model that it is representing
@Repository
public interface ActivityRepository extends MongoRepository<Activity, String> {

    //Hibernate will automatically generate the query for below BTS and will
    //return the list of activity of the user with given userId
    List<Activity> findByUserId(String userId);
}
