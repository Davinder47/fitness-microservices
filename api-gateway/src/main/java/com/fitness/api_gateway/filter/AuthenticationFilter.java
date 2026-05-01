package com.fitness.api_gateway.filter;

import com.fitness.api_gateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.List;

@Component("AuthenticationFilter")
public class AuthenticationFilter implements HandlerFilterFunction<ServerResponse, ServerResponse> {

    @Autowired
    private JwtUtil jwtUtil;

    public static final List<String> openEndpoints = List.of(
            "/api/users/register",
            "/api/users/login",
            "/eureka",
            "/actuator"
    );

    @Override
    public ServerResponse filter(ServerRequest request, HandlerFunction<ServerResponse> next) throws Exception {

        String path = request.uri().getPath(); // Using uri().getPath() is more reliable
        System.out.println("Incoming request path: " + path); // Debugging line

        System.out.println("Checking path: " + path);
        // Check if path starts with or contains the open endpoints
        boolean isPublicEndpoint = openEndpoints.stream()
                .anyMatch(path::contains);

        System.out.println("Is public endpoint: " + isPublicEndpoint);

        if (!isPublicEndpoint) {
            List<String> authHeaders = request.headers().asHttpHeaders().get("Authorization");

            if (authHeaders == null || authHeaders.isEmpty()) {
                System.out.println("Missing Authorization Header for: " + path);
                return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
            }

            String authHeader = authHeaders.get(0);

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                authHeader = authHeader.substring(7);
            } else {
                return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
            }

            try {
                jwtUtil.validateToken(authHeader);
            } catch (Exception e) {
                System.out.println("Token validation failed: " + e.getMessage());
                return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
            }
        }

        return next.handle(request);
    }
}