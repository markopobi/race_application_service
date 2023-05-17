package com.race.app.race_application_command_service.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurer
{

    // Inject the JWT set URI from application properties
    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    String jwkSetUri;

    // Inject the JWT request filter
    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfigurer(JwtRequestFilter jwtRequestFilter)
    {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    // Configure the JWT decoder bean
    @Bean
    public JwtDecoder jwtDecoder()
    {
        return NimbusJwtDecoder.withJwkSetUri(this.jwkSetUri).build();
    }

    // Configure the password encoder bean
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    // Configure the security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {
        // Disable CSRF, authorize requests, permit all requests to /authenticate, authenticate all other requests,
        // and configure session management policy
        httpSecurity.csrf().disable().authorizeHttpRequests().requestMatchers("/authenticate").permitAll().anyRequest()
                .authenticated().and().exceptionHandling().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Add the JWT request filter before the UsernamePasswordAuthenticationFilter
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        // Configure OAuth2 resource server with JWT
        return httpSecurity.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt).build();
    }

    // Configure the authentication manager bean
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception
    {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class).build();
    }
}
