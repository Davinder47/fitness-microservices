package com.example.activityservice.model;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "activities")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Activity {

    @Id
    private String id;
    private String userId;
    private ActivityType type;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;

    @Field("metrics")//This adds custom name to additionalMetrics
    private Map<String, Object> additionalMetrics;
    // Above will include anything additional which user want to store, it
    // will allow flexible storage, so if you're importing activity data from
    // any other data source, you can use map for that.

    @CreatedDate// It will autopopulate the createdAt
    private LocalDateTime createdAt;

    @LastModifiedDate//This will autopopulate the updatedAt when the document
    // will be updated
    private LocalDateTime updatedAt;

    // Created and Updated time stamp are hibernate specific implementations,
    // and they are used in JPA based relational applications.
}
