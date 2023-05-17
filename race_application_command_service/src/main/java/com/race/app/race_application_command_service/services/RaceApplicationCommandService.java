package com.race.app.race_application_command_service.services;

import java.util.Map;
import java.util.UUID;

import com.race.app.race_application_command_service.domain.RaceApplication;

public interface RaceApplicationCommandService
{
    RaceApplication create(RaceApplication raceApplication);

    RaceApplication findById(UUID id);

    void delete(RaceApplication existingRaceApplication);

    RaceApplication patch(UUID id, Map<String, Object> fields);
}
