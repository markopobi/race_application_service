package com.race.app.race_application_command_service.auth;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@SpringBootTest
public class JwtUtilTest
{

    @InjectMocks
    private JwtUtil jwtUtil;

    @Mock
    private UserDetails userDetails;

    private final String secret = "mysecretkeymysecretkeymysecretkeymysecretkeymysecretkey";

    private String token;

    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(jwtUtil, "secret", secret);
        Map<String, Object> claims = new HashMap<>();
        token = Jwts.builder().setClaims(claims).setSubject("test")
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8))).compact();
    }

    @Test
    public void testExtractUsername()
    {
        String username = jwtUtil.extractUsername(token);
        assertEquals("test", username);
    }

    @Test
    public void testValidateToken()
    {
        when(userDetails.getUsername()).thenReturn("test");
        assertTrue(jwtUtil.validateToken(token, userDetails));
    }

    @Test
    public void testGenerateToken()
    {
        when(userDetails.getUsername()).thenReturn("test");
        String token = jwtUtil.generateToken(userDetails);
        assertNotNull(token);
    }

    @Test
    public void testIsTokenExpired()
    {
        assertFalse(jwtUtil.isTokenExpired(token));
    }
}
