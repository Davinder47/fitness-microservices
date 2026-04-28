package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

//listener which will listen to messages which are posted in a particular
//queue , then it will retrieve that message and we will be printing that
//message on the console for now, to show us how we can read the message
//from a queue.
@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener {

    private final ActivityAIService aiservice;

    @RabbitListener(queues = "activity.queue")
    public void processActivity(Activity activity) {
        try {
            log.info("Received activity for processing: {}", activity.getId());
            String recommendation = aiservice.generateRecommendation(activity);
            log.info("Generated Recommendation: {}", recommendation);
        } catch (Exception e) {
            log.error("Error processing activity: {}", e.getMessage());
            // don't rethrow - prevents service from crashing
        }
    }


}
