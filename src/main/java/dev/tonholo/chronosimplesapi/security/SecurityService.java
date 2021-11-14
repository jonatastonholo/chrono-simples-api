package dev.tonholo.chronosimplesapi.security;

import dev.tonholo.chronosimplesapi.config.SecurityConfig;
import dev.tonholo.chronosimplesapi.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest.Headers;

import static dev.tonholo.chronosimplesapi.domain.HttpHeaders.X_API_KEY;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityService {
    private final SecurityConfig securityConfig;

    public void authorize(Headers headers) {
        final var authorizations = headers.header(X_API_KEY);

        if (authorizations.isEmpty() || Strings.isBlank(authorizations.get(0))) {
            log.warn("Unauthorized user without authorization key");
            throw new UnauthorizedException();
        }

        if (!securityConfig.getApiKey().equals(authorizations.get(0))) {
            log.warn("Unauthorized user with invalid authorization key");
            throw new UnauthorizedException();
        }
    }
}

