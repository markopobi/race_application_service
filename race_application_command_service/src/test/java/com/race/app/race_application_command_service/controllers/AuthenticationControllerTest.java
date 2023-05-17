package com.race.app.race_application_command_service.controllers;

import java.util.ArrayList;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.race.app.race_application_command_service.auth.AuthenticationRequest;
import com.race.app.race_application_command_service.auth.AuthenticationResponse;
import com.race.app.race_application_command_service.auth.JwtUtil;
import com.race.app.race_application_command_service.auth.MyUserDetailsService;

public class AuthenticationControllerTest
{

    private AuthenticationController authenticationController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private MyUserDetailsService userDetailsService;

    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.openMocks(this);
        authenticationController = new AuthenticationController(authenticationManager, userDetailsService, jwtUtil);
    }

    @Test
    public void createAuthenticationToken_WhenCredentialsAreValid_ReturnsJwt()
    {
        //given
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("test", "password");
        UserDetails userDetails = new User("test", "password", new ArrayList<>());

        when(userDetailsService.loadUserByUsername("test")).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails)).thenReturn("jwt");

        //when
        ResponseEntity<?> response = authenticationController.createAuthenticationToken(authenticationRequest);

        //then
        assertEquals("jwt", ((AuthenticationResponse) Objects.requireNonNull(response.getBody())).getJwt());
    }

    @Test
    public void createAuthenticationToken_WhenCredentialsAreInvalid_ThrowsException()
    {
        //given
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("test", "password");

        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()))).thenThrow(
                new BadCredentialsException("Bad Credentials"));

        //when and then
        assertThrows(ResponseStatusException.class,
                () -> authenticationController.createAuthenticationToken(authenticationRequest));
    }
}
