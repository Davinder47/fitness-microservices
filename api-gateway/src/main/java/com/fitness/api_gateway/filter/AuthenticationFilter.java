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
import java.util.function.Predicate;

@Component
public class AuthenticationFilter implements HandlerFilterFunction<ServerResponse, ServerResponse> {

    @Autowired
    private JwtUtil jwtUtil;

    public static final List<String> openEndpoints = List.of(
            "/api/users/register",
            "/api/users/login",
            "/eureka"
    );

    @Override
    // Changed 'handle' to 'filter' to match the interface requirements
    public ServerResponse filter(ServerRequest request, HandlerFunction<ServerResponse> next) throws Exception {

        Predicate<ServerRequest> isSecured = r -> openEndpoints.stream()
                .noneMatch(uri -> r.path().contains(uri));

        if (isSecured.test(request)) {
            List<String> authHeaders = request.headers().asHttpHeaders().get("Authorization");

            if (authHeaders == null || authHeaders.isEmpty()) {
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
                return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
            }
        }

        // Changed this call to 'next.handle(request)' as well
        return next.handle(request);
    }
}