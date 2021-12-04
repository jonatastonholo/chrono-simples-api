package dev.tonholo.chronosimplesapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
public class CorsConfig implements WebFluxConfigurer {
    private static final String[] ALLOWED_METHODS = {
            "GET",
            "POST",
            "PUT",
            "DELETE",
            "TRACE",
            "OPTIONS",
            "PATCH",
            "CONNECT",
            "HEAD"
    };

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry
                .addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods(ALLOWED_METHODS)
                .allowedHeaders("*")
                .allowedOriginPatterns("*")
                .maxAge(3600);
    }
}