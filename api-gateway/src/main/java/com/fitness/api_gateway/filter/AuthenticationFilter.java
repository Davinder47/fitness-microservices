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
            "/api/users/auth/login",
            "/eureka"
    );

    @Override
    public ServerResponse filter(ServerRequest request, HandlerFunction<ServerResponse> next) throws Exception {

        String path = request.uri().getPath();
        System.out.println("Incoming path: " + path);

        boolean isPublic = openEndpoints.stream().anyMatch(path::contains);

        if (!isPublic) {
            List<String> authHeaders = request.headers().asHttpHeaders().get("Authorization");

            if (authHeaders == null || authHeaders.isEmpty()) {
                return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
            }

            String token = authHeaders.get(0);

            if (!token.startsWith("Bearer ")) {
                return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
            }

            token = token.substring(7);

            try {
                jwtUtil.validateToken(token);
            } catch (Exception e) {
                return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
            }
        }

        return next.handle(request);
    }
}