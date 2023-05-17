package com.race.app.race_application_command_service.auth;

import lombok.Getter;

/**
 * Represents an authentication response which contains the generated JSON Web Token (JWT).
 */
@Getter
public class AuthenticationResponse
{
    /**
     * The JSON Web Token (JWT) generated for the user after successful authentication.
     */
    private final String jwt;

    /**
     * Constructor for the AuthenticationResponse class.
     *
     * @param jwt The JSON Web Token (JWT) string.
     */
    public AuthenticationResponse(String jwt)
    {
        this.jwt = jwt;
    }

}
