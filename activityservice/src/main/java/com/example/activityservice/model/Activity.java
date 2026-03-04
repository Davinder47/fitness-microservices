package com.example.activityservice.model;

import java.time.LocalDateTime;
import java.util.Map;

public class Activity {

    private String id;
    private String userId;
    private ActivityType type;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;
    private Map<String, Object> additionalMetrics;
    // Above will include anything additional which user want to store, it
    // will allow flexible storage, so if you're importing activity data from
    // any other data source, you can use map for that.
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
