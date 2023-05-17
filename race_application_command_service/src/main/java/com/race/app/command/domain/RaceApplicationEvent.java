package com.race.app.command.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an race application event containing the necessary
 * credentials: type and race application.
 */
@Getter
@Setter
@AllArgsConstructor
public class RaceApplicationEvent {

    private String type;
    private RaceApplication raceApplication;

}
