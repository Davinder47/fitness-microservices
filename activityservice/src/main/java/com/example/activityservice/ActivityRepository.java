package com.example.activityservice;

import com.example.activityservice.model.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

//This repository will help us to save the data into the DB
//MongoRepository is an interface which already exists
//Activity is the model that it is representing
@Repository
public interface ActivityRepository extends MongoRepository<Activity, String> {

}
