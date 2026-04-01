package com.example.activityservice.config;


import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced // Allows web client to resolve the service name via eureka,
    // means we don't want to refer the hardcore url i.e localhost, we will only
    // use application name to refer i.e "user-service" or "Activity-Service"
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }

    //Bean to configure to user-service
    @Bean
    public WebClient userServiceClient(WebClient.Builder webClientBuilder){
        return webClientBuilder
                .baseUrl("http://USER-SERVICE")
                .build();
    //by using this we can valid the particular user and we can actually
    //call USER-SERVICE
    }
}
