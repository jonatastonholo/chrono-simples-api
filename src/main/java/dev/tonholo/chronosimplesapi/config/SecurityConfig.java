package dev.tonholo.chronosimplesapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class SecurityConfig {

    private final String apiKey;
    public SecurityConfig(Environment environment) {
        apiKey = environment.getProperty("server.security.api-key");
    }

    public String getApiKey() {
        return apiKey;
    }
}
