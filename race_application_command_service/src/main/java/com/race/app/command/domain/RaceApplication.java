package com.race.app.command.domain;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents an Race application entity as JPA entity containing the necessary
 * fields: id, firstName, lastName and distance. Field club is optional
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class RaceApplication implements Serializable
{

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Size(max = 255)
    private String firstName;

    @NotNull
    @Size(max = 255)
    private String lastName;

    @Size(max = 255)
    private String club;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Distance distance;

}
