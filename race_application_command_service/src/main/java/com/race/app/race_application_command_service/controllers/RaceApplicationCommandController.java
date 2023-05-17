package com.race.app.race_application_command_service.controllers;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.race.app.race_application_command_service.domain.RaceApplication;
import com.race.app.race_application_command_service.services.RaceApplicationCommandService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/race-application")
public class RaceApplicationCommandController
{

    private final RaceApplicationCommandService raceApplicationService;

    public RaceApplicationCommandController(RaceApplicationCommandService raceApplicationService)
    {
        this.raceApplicationService = raceApplicationService;
    }

    @PreAuthorize("hasAuthority('race_application:write')")
    @PostMapping
    public ResponseEntity<RaceApplication> create(
            @RequestBody @Valid RaceApplication raceApplication)
    {
        log.debug("Creating race application for id: " + raceApplication.getId());
        RaceApplication savedRaceApplication = raceApplicationService.create(raceApplication);
        return new ResponseEntity<>(savedRaceApplication, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('race_application:patch')")
    @PatchMapping("/{id}")
    public ResponseEntity<RaceApplication> patch(@PathVariable UUID id, @RequestBody Map<String, Object> fields)
    {
        RaceApplication existingRaceApplication = raceApplicationService.findById(id);
        if (existingRaceApplication == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.debug("Patching race application for id: " + id);
        RaceApplication updatedRaceApplication = raceApplicationService.patch(id, fields);
        return new ResponseEntity<>(updatedRaceApplication, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('race_application:delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id)
    {
        RaceApplication existingRaceApplication = raceApplicationService.findById(id);
        if (existingRaceApplication == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.debug("Deleting race application for id: " + id);
        raceApplicationService.delete(existingRaceApplication);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
