package com.race.app.race_application_command_service.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an authentication request containing the necessary
 * credentials: a username and a password.
 */
@Getter
@Setter
@AllArgsConstructor
public class AuthenticationRequest
{
    /**
     * The username of the user attempting to authenticate.
     */
    private String username;

    /**
     * The password of the user attempting to authenticate.
     */
    private String password;
}
