package com.restaurantwebsite.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    // Uses a pattern instead of an exact port list — Vite auto-picks the
    // next free port (5173, 5174, 5176, ...) whenever earlier ones are
    // still occupied by leftover dev server processes, so pinning to
    // specific ports kept breaking every time that happened. This allows
    // any port on localhost during development. In production, replace
    // this with the actual deployed frontend domain instead.
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOriginPatterns("http://localhost:*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }
}