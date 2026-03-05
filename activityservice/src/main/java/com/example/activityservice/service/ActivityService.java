package com.example.activityservice.service;

import com.example.activityservice.dto.ActivityRequest;
import com.example.activityservice.dto.ActivityResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {
    //When the user will say trackActivity, we will actually save it into DB
    public ActivityResponse trackActivity(ActivityRequest request) {
    }
}
