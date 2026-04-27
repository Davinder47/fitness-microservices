package com.fitness.aiservice.model;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class Activity implements Serializable {

    private String id;
    private String userId;
    private String type;        // String to avoid enum issues
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;
    private Map<String, Object> additionalMetrics;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}