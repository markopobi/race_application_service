package com.race.app.race_application_query_service.services;

import java.util.List;
import java.util.UUID;

import com.race.app.race_application_query_service.domain.RaceApplication;
import com.race.app.race_application_query_service.domain.RaceApplicationEvent;

public interface RaceApplicationQueryService
{

    List<RaceApplication> getAllRaceApplications();

    RaceApplication getRaceApplication(UUID id);

    void consume(RaceApplicationEvent raceApplicationEvent);
}
