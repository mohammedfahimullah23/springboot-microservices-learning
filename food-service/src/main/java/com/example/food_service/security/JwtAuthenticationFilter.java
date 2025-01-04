package com.example.food_service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Set<String> EXCLUDED_PATHS = Set.of("/api/auth/", "/actuator/health", "/actuator/info");
    private final JwtUtil jwtUtil;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (!"prod".equalsIgnoreCase(activeProfile)) {
            // Continue with the filter logic for non-prod profiles
            for (String path : EXCLUDED_PATHS) {
                if (request.getRequestURI().startsWith(path)) {
                    filterChain.doFilter(request, response); // Skip this filter
                    return;
                }
            }

            String token = extractToken(request);
            String username = extractUsername(token);
            if (token != null && jwtUtil.validateToken(token, username)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);  // Proceed with the filter chain
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // If invalid token
            }
        } else {
            filterChain.doFilter(request, response);
        }

    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);  // Extract the token after "Bearer "
        }
        return null;
    }

    private String extractUsername(String token) {
        return jwtUtil.extractUsername(token);  // Extract the username from the token
    }
}



