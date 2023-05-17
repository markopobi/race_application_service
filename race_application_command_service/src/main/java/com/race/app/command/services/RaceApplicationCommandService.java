package com.race.app.command.services;

import java.util.Map;
import java.util.UUID;

import com.race.app.command.domain.RaceApplication;

public interface RaceApplicationCommandService
{
    RaceApplication create(RaceApplication raceApplication);

    RaceApplication findById(UUID id);

    void delete(RaceApplication existingRaceApplication);

    RaceApplication patch(UUID id, Map<String, Object> fields);
}
