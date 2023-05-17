package com.race.app.race_application_command_service.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;

import com.race.app.race_application_command_service.domain.Distance;
import com.race.app.race_application_command_service.domain.RaceApplication;
import com.race.app.race_application_command_service.services.RaceApplicationCommandService;

@ExtendWith(MockitoExtension.class)
public class RaceApplicationCommandControllerTest
{

    @Mock
    private RaceApplicationCommandService raceApplicationCommandService;

    @InjectMocks
    private RaceApplicationCommandController raceApplicationCommandController;

    private RaceApplication raceApplication;

    @BeforeEach
    void setUp()
    {
        raceApplication = new RaceApplication();
        raceApplication.setId(UUID.randomUUID());
        raceApplication.setFirstName("First");
        raceApplication.setLastName("Last");
        raceApplication.setDistance(Distance.MARATHON);
    }

    @Test
    void testCreateRaceApplication()
    {
        // Given
        when(raceApplicationCommandService.create(any(RaceApplication.class))).thenReturn(raceApplication);

        // When
        ResponseEntity<RaceApplication> response = raceApplicationCommandController.create(raceApplication);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(raceApplication, response.getBody());
    }

    @Test
    void testPatchRaceApplication()
    {
        // Given
        RaceApplication raceApplicationNew = new RaceApplication();
        raceApplicationNew.setId(UUID.randomUUID());
        raceApplicationNew.setFirstName("First");
        raceApplicationNew.setLastName("Last");
        raceApplicationNew.setClub("Club");
        raceApplicationNew.setDistance(Distance.MARATHON);

        Map<String, Object> fields = new HashMap<>();
        fields.put("club", "Club");
        when(raceApplicationCommandService.findById(any(UUID.class))).thenReturn(raceApplication);
        when(raceApplicationCommandService.patch(any(UUID.class), anyMap())).thenReturn(raceApplicationNew);

        // When
        ResponseEntity<RaceApplication> response = raceApplicationCommandController.patch(raceApplication.getId(),
                fields);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(raceApplicationNew, response.getBody());
    }

    @Test
    void testDeleteRaceApplication()
    {
        // Given
        when(raceApplicationCommandService.findById(any(UUID.class))).thenReturn(raceApplication);

        // When
        ResponseEntity<Void> response = raceApplicationCommandController.delete(raceApplication.getId());

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testPatchRaceApplicationNotFound()
    {
        // Given
        when(raceApplicationCommandService.findById(any(UUID.class))).thenReturn(null);
        Map<String, Object> fields = new HashMap<>();

        // When
        ResponseEntity<RaceApplication> response = raceApplicationCommandController.patch(UUID.randomUUID(), fields);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteRaceApplicationNotFound()
    {
        // Given
        when(raceApplicationCommandService.findById(any(UUID.class))).thenReturn(null);

        // When
        ResponseEntity<Void> response = raceApplicationCommandController.delete(UUID.randomUUID());

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
