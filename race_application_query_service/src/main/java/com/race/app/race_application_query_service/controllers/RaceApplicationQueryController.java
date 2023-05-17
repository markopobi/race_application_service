package com.race.app.race_application_query_service.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.race.app.race_application_query_service.domain.RaceApplication;
import com.race.app.race_application_query_service.services.RaceApplicationQueryService;

@RestController
@RequestMapping("/api/race-applications")
public class RaceApplicationQueryController
{

    private final RaceApplicationQueryService raceApplicationQueryService;

    public RaceApplicationQueryController(RaceApplicationQueryService raceApplicationQueryService)
    {
        this.raceApplicationQueryService = raceApplicationQueryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RaceApplication> getRaceApplication(@PathVariable UUID id)
    {
        RaceApplication raceApplication = raceApplicationQueryService.getRaceApplication(id);
        return ResponseEntity.ok(raceApplication);
    }

    @GetMapping
    public ResponseEntity<List<RaceApplication>> getAllRaceApplications()
    {
        List<RaceApplication> raceApplications = raceApplicationQueryService.getAllRaceApplications();
        return ResponseEntity.ok(raceApplications);
    }
}
