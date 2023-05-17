package com.race.app.race_application_command_service.auth;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Custom filter for JWT authentication.
 * It extends OncePerRequestFilter provided by Spring Security to ensure that this filter is called once per request.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter
{

    /**
     * Service that provides user details for the system.
     */
    private final MyUserDetailsService userDetailsService;

    /**
     * Service that provides JWT related operations like extraction of username, validation of token, etc.
     */
    private final JwtUtil jwtUtil;

    /**
     * Constructs a JwtRequestFilter with provided MyUserDetailsService and JwtUtil.
     * @param userDetailsService The service to fetch user details.
     * @param jwtUtil The utility to perform JWT related operations.
     */
    public JwtRequestFilter(MyUserDetailsService userDetailsService, JwtUtil jwtUtil)
    {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * This method is the core method of this filter which is called for each request.
     * It extracts the JWT from the Authorization header, and if the token is valid, it sets the authentication in the context.
     *
     * @param request The servlet request.
     * @param response The servlet response.
     * @param chain The filter chain.
     * @throws ServletException if a servlet-specific error occurs.
     * @throws IOException if an input or output error is detected when the servlet handles the request.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException
    {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
        {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails))
            {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
