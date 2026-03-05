package com.example.activityservice.dto;

import com.example.activityservice.model.ActivityType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data //To get getters and setters etc.
public class ActivityRequest {

    private String userId; //Whose activity will be recorded
    private ActivityType type; //This is an enum which we already defined,
    //and this is the type of activity which will be recorded
    private Integer duration; // Duration of activity will be recorded
    private Integer caloriesBurned; //This will be recorded too
    private LocalDateTime startTime; //Denoting the activity start time
    private Map<String, Object> additionalMetrics;
}
