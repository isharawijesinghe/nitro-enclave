package com.nitro.host.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/ping", "/api/echo", "/api/**", "/api/publish").permitAll()  // Allow unauthenticated access to specific endpoints
                        .anyRequest().authenticated()  // Require authentication for all other endpoints
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/ping", "/api/echo", "/api/**", "/api/publish")  // Disable CSRF protection for specific endpoints
                );

        return http.build();
    }

}
