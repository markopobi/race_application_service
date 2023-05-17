package com.race.app.query.services;

import java.util.List;
import java.util.UUID;

import com.race.app.query.domain.RaceApplication;
import com.race.app.query.domain.RaceApplicationEvent;

public interface RaceApplicationQueryService
{

    List<RaceApplication> getAllRaceApplications();

    RaceApplication getRaceApplication(UUID id);

    void consume(RaceApplicationEvent raceApplicationEvent);
}
