package com.race.app.race_application_command_service.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an User entity as JPA entity containing the necessary
 * fields: id, username, password and role.
 */
@Getter
@Setter
@Entity
public class UserEntity
{
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Size(max = 255)
    private String username;

    @NotNull
    @Size(max = 255)
    private String password;

    @NotNull
    @Size(max = 255)
    private String role;
}
