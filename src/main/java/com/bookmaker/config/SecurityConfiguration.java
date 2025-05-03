package com.bookmaker.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final Logger log = LogManager.getLogger(getClass());

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable HSTS for Docker/local use
                .headers(headers -> headers.httpStrictTransportSecurity().disable())

                // CSRF protection disabled for simplicity
                .csrf().disable()

                // CORS disabled unless specific origins are needed
                .cors().disable()

                // URL-based access rules
                .authorizeRequests(auth -> auth
                        .antMatchers("/api/**", "/actuator/**").permitAll()
                        .anyRequest().denyAll()
                );

        log.debug("Initialized Spring MVC Security Configuration");
        return http.build();
    }
}
