package com.example.activityservice.controller;


import com.example.activityservice.dto.ActivityRequest;
import com.example.activityservice.dto.ActivityResponse;
import com.example.activityservice.service.ActivityService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@AllArgsConstructor//Using this spring will automatically inject "activityservice"
//dependency, otherwise you will get null point exception when you try
//to make use of activity service
public class ActivityController {

    private ActivityService activityService;

    //ActivityResponse class is going to be a DTO class that is going to return
    //the response, and this class is going to define the response.
    //ActivityRequest is also a DTO
    @PostMapping //This mapping will help us to track the activity
    public ResponseEntity<ActivityResponse> trackActivity(@RequestBody ActivityRequest request){
        return ResponseEntity.ok(activityService.trackActivity(request));
    }

    @GetMapping//This mapping will help us to get the activity of a user
    public ResponseEntity<List<ActivityResponse>> getUserActivities(@RequestHeader("X-User-ID") String userId){
        //ActivityResponse represents single activity and a user can have
        //multiple activities, that's why we are using List here
        return ResponseEntity.ok(activityService.getUserActivities(userId));
    }

    //Every activity has an id, when the user will pass the activity id we,
    //should be able to filter out that particular activity
    @GetMapping("/{activityId}")//This mapping will help us to get the activity of a user
    public ResponseEntity<ActivityResponse> getActivity(@PathVariable String activityId){
        //ActivityResponse represents single activity and a user can have
        //multiple activities, that's why we used list here
        return ResponseEntity.ok(activityService.getActivityById(activityId));
    }

}
