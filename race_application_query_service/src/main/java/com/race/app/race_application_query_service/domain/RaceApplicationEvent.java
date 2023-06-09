package com.race.app.race_application_query_service.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a race application event containing the necessary
 * credentials: type and race application.
 */
@Getter
@Setter
@AllArgsConstructor
public class RaceApplicationEvent
{
    private String type;

    private RaceApplication raceApplication;
}
