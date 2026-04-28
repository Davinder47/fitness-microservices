package com.fitness.aiservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAIService {

    private final GeminiService geminiService;
    private final RecommendationRepository recommendationRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Recommendation generateRecommendation(Activity activity) {
        String prompt = createPromptForActivity(activity);
        String aiResponse = geminiService.getAnswer(prompt);
        String extractedText = extractTextFromResponse(aiResponse);
        return processAndSaveRecommendation(activity, extractedText);
    }

    private String extractTextFromResponse(String aiResponse) {
        try {
            JsonNode root = objectMapper.readTree(aiResponse);
            String text = root.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();

            text = text.replace("```json", "")
                    .replace("```", "")
                    .trim();

            return text;
        } catch (Exception e) {
            log.error("Error extracting text from Gemini response: {}", e.getMessage());
            return aiResponse;
        }
    }

    private Recommendation processAndSaveRecommendation(Activity activity, String responseText) {
        try {
            JsonNode root = objectMapper.readTree(responseText);

            // Extract analysis as single string
            String analysis = root.path("analysis").toString();

            // Extract improvements as List<String>
            List<String> improvements = new ArrayList<>();
            root.path("improvements").forEach(node ->
                    improvements.add(node.path("area").asText()
                            + ": " + node.path("recommendation").asText())
            );

            // Extract suggestions as List<String>
            List<String> suggestions = new ArrayList<>();
            root.path("suggestions").forEach(node ->
                    suggestions.add(node.path("workout").asText()
                            + ": " + node.path("description").asText())
            );

            // Extract safety as List<String>
            List<String> safety = new ArrayList<>();
            root.path("safety").forEach(node ->
                    safety.add(node.asText())
            );

            // Build and save recommendation
            Recommendation recommendation = Recommendation.builder()
                    .activityId(activity.getId())
                    .userId(activity.getUserId())
                    .activityType(activity.getType())
                    .recommendation(analysis)
                    .improvements(improvements)
                    .suggestions(suggestions)
                    .safety(safety)
                    .build();

            Recommendation saved = recommendationRepository.save(recommendation);
            log.info("Recommendation saved to MongoDB with id: {}", saved.getId());
            return saved;

        } catch (Exception e) {
            log.error("Error processing recommendation: {}", e.getMessage());
            return null;
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