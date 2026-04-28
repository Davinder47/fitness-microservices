package com.fitness.aiservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.aiservice.model.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAIService {

    private final GeminiService geminiService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String generateRecommendation(Activity activity) {
        String prompt = createPromptForActivity(activity);
        String aiResponse = geminiService.getAnswer(prompt);
        return extractTextFromResponse(aiResponse);
    }

    private String extractTextFromResponse(String aiResponse) {
        try {
            JsonNode root = objectMapper.readTree(aiResponse);
            String text = root
                    .path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();
            log.info("RESPONSE FROM AI: {}", text);
            return text;
        } catch (Exception e) {
            log.error("Error parsing Gemini response: {}", e.getMessage());
            return aiResponse; // fallback to raw response
        }
    }

    private String createPromptForActivity(Activity activity) {
        return String.format("""
            Analyze this fitness activity and provide detailed recommendations in the following EXACT JSON format:
            {
                "analysis": {
                    "overall": "Overall analysis here",
                    "pace": "Pace analysis here",
                    "heartRate": "Heart rate analysis here",
                    "caloriesBurned": "Calories burned analysis here"
                },
                "improvements": [
                    {
                        "area": "Area name",
                        "recommendation": "Detailed recommendation"
                    }
                ],
                "suggestions": [
                    {
                        "workout": "Workout name",
                        "description": "Detailed workout description"
                    }
                ],
                "safety": [
                    "Safety point 1",
                    "Safety point 2"
                ]
            }

            Analyze this activity:
            Activity Type: %s
            Duration: %d minutes
            Calories Burned: %d
            Additional Metrics: %s

            Provide detailed analysis focusing on performance, improvements, next workout suggestions, and safety guidelines.
            Ensure the response follows the EXACT JSON format shown above.
            """,
                activity.getType(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                activity.getAdditionalMetrics()
        );
    }
}